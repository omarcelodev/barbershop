package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Canal;
import com.marcelo.barbershop.entity.Notificacao;
import com.marcelo.barbershop.entity.StatusNotificacao;
import java.time.LocalDateTime;

public record NotificacaoResponse(
    Long id,
    Long agendamentoId,
    Canal canal,
    String mensagem,
    StatusNotificacao status,
    LocalDateTime criadoEm,
    LocalDateTime enviadoEm
) {
    public static NotificacaoResponse from(Notificacao notificacao) {
        return new NotificacaoResponse(
            notificacao.getId(),
            notificacao.getAgendamento().getId(),
            notificacao.getCanal(),
            notificacao.getMensagem(),
            notificacao.getStatus(),
            notificacao.getCriadoEm(),
            notificacao.getEnviadoEm()
        );
    }
}