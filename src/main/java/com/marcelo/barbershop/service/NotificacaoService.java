package com.marcelo.barbershop.service;

import com.marcelo.barbershop.entity.Agendamento;
import com.marcelo.barbershop.entity.Canal;
import com.marcelo.barbershop.entity.Notificacao;
import com.marcelo.barbershop.entity.StatusNotificacao;
import com.marcelo.barbershop.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final AgendamentoService agendamentoService;

    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              AgendamentoService agendamentoService) {
        this.notificacaoRepository = notificacaoRepository;
        this.agendamentoService = agendamentoService;
    }

    public List<Notificacao> listarPorAgendamento(Long agendamentoId) {
        return notificacaoRepository.findAllByAgendamentoId(agendamentoId);
    }

    public List<Notificacao> listarPendentes() {
        return notificacaoRepository.findAllByStatus(StatusNotificacao.PENDENTE);
    }

    public List<Notificacao> listarFalhas() {
        return notificacaoRepository.findAllByStatusOrderByCriadoEmAsc(StatusNotificacao.FALHOU);
    }

    @Transactional
    public Notificacao criar(Long agendamentoId, Canal canal, String mensagem) {
        Agendamento agendamento = agendamentoService.buscarPorId(agendamentoId);

        Notificacao notificacao = new Notificacao();
        notificacao.setAgendamento(agendamento);
        notificacao.setCanal(canal);
        notificacao.setMensagem(mensagem);

        return notificacaoRepository.save(notificacao);
    }

    @Transactional
    public void marcarComoEnviada(Long id) {
        Notificacao notificacao = buscarPorId(id);
        notificacao.marcarComoEnviada();
        notificacaoRepository.save(notificacao);
    }

    @Transactional
    public void marcarComoFalha(Long id) {
        Notificacao notificacao = buscarPorId(id);
        notificacao.marcarComoFalha();
        notificacaoRepository.save(notificacao);
    }

    private Notificacao buscarPorId(Long id) {
        return notificacaoRepository.findById(id)
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Notificação não encontrada: " + id));
    }
}