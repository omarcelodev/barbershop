package com.marcelo.barbershop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcelo.barbershop.entity.Agendamento;
import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.Servico;
import com.marcelo.barbershop.entity.Usuario;
import com.marcelo.barbershop.repository.AgendamentoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class AgendamentoService {
    
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioService usuarioService;
    private final BarbeiroService barbeiroService;
    private final ServicoService servicoService;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, UsuarioService usuarioService, BarbeiroService barbeiroService, ServicoService servicoService) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioService = usuarioService;
        this.barbeiroService = barbeiroService;
        this.servicoService = servicoService;
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado: " + id));
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
    public Agendamento criar(Long usuarioId, Long barbeiroId, Long servicoId, LocalDateTime inicio ) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Barbeiro barbeiro = barbeiroService.buscarPorId(barbeiroId);
        Servico servico = servicoService.buscarPorId(servicoId);

        if (!barbeiro.podeReceberAgendamento()) {
            throw new IllegalStateException("Barbeiro indiponível para agendamentos");
        }

        if (!barbeiro.atendeServico(servico)) {
            throw new IllegalStateException("Barbeiro não realiza serviço solicitado");
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuario(usuario);
        agendamento.setBarbeiro(barbeiro);
        agendamento.definirHorario(inicio, servico);

        verificarConflito(barbeiroId, agendamento.getDataHoraInicio(), agendamento.getDataHoraFim(), null);

        try {
            return agendamentoRepository.save(agendamento);
        } catch (OptimisticEntityLockException e) {
            throw new IllegalStateException("Horário ocupado por outro agendamento simultâneo. Tente novamente.", e);
        }
    }
}
