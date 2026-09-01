package com.marcelo.barbershop.service;

import com.marcelo.barbershop.entity.*;
import com.marcelo.barbershop.repository.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioService usuarioService;
    private final BarbeiroService barbeiroService;
    private final ServicoService servicoService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              UsuarioService usuarioService,
                              BarbeiroService barbeiroService,
                              ServicoService servicoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioService = usuarioService;
        this.barbeiroService = barbeiroService;
        this.servicoService = servicoService;
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado: " + id));
    }

    public List<Agendamento> listarPorUsuario(Long usuarioId) {
        return agendamentoRepository.findAllByUsuarioId(usuarioId);
    }

    public List<Agendamento> listarPorBarbeiro(Long barbeiroId) {
        return agendamentoRepository.findAllByBarbeiroId(barbeiroId);
    }

    public List<Agendamento> listarAgendaDoBarbeiro(Long barbeiroId, LocalDateTime inicioDia, LocalDateTime fimDia) {
        return agendamentoRepository.findByBarbeiroEDia(barbeiroId, inicioDia, fimDia);
    }

    @Transactional
    public Agendamento criar(Long usuarioId, Long barbeiroId, Long servicoId, LocalDateTime inicio) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Barbeiro barbeiro = barbeiroService.buscarPorId(barbeiroId);
        Servico servico = servicoService.buscarPorId(servicoId);

        if (!barbeiro.podeReceberAgendamento()) {
            throw new IllegalStateException("Barbeiro indisponível para agendamentos");
        }

        if (!barbeiro.atendeServico(servico)) {
            throw new IllegalStateException("Barbeiro não realiza o serviço solicitado");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuario(usuario);
        agendamento.setBarbeiro(barbeiro);
        agendamento.definirHorario(inicio, servico);

        verificarConflito(barbeiroId, agendamento.getDataHoraInicio(), agendamento.getDataHoraFim(), null);

        try {
            return agendamentoRepository.save(agendamento);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Horário ocupado por outro agendamento simultâneo. Tente novamente.", e);
        }
    }

    @Transactional
    public Agendamento reagendar(Long id, LocalDateTime novoInicio) {
        Agendamento agendamento = buscarPorId(id);

        if (!agendamento.isAtivo()) {
            throw new IllegalStateException("Apenas agendamentos ativos podem ser reagendados");
        }

        agendamento.definirHorario(novoInicio, agendamento.getServico());
        agendamento.setStatus(Status.REAGENDADO);

        verificarConflito(
            agendamento.getBarbeiro().getId(),
            agendamento.getDataHoraInicio(),
            agendamento.getDataHoraFim(),
            id
        );

        try {
            return agendamentoRepository.save(agendamento);
        } catch (OptimisticLockException e) {
            throw new IllegalStateException("Horário ocupado por outro agendamento simultâneo. Tente novamente.", e);
        }
    }

    @Transactional
    public void cancelar(Long id) {
        Agendamento agendamento = buscarPorId(id);

        if (!agendamento.isAtivo()) {
            throw new IllegalStateException("Agendamento já está encerrado ou cancelado");
        }

        agendamento.cancelar();
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void concluir(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setStatus(Status.CONCLUIDO);
        agendamentoRepository.save(agendamento);
    }

    // =========================
    // Verificação de conflito
    // =========================

    /**
     * Verifica se existe conflito de horário para o barbeiro.
     * O parâmetro ignorarId exclui o próprio agendamento na verificação (usado no reagendamento).
     */
    private void verificarConflito(Long barbeiroId, LocalDateTime inicio, LocalDateTime fim, Long ignorarId) {
        List<Agendamento> conflitos = agendamentoRepository.findConflitantes(
            barbeiroId, inicio, fim,
            List.of(Status.AGENDADO, Status.CONFIRMADO)
        );

        boolean temConflito = conflitos.stream()
            .anyMatch(a -> !a.getId().equals(ignorarId));

        if (temConflito) {
            throw new IllegalStateException("Horário indisponível: conflito com agendamento existente");
        }
    }
}