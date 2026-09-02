package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Agendamento;
import com.marcelo.barbershop.entity.Status;
import java.time.Instant;
import java.time.LocalDateTime;

public record AgendamentoResponse(
    Long id,
    UsuarioResponse usuario,
    BarbeiroResponse barbeiro,
    ServicoResponse servico,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    long duracaoEmMinutos,
    Status status,
    Instant criadoEm
) {
    public static AgendamentoResponse from(Agendamento agendamento) {
        return new AgendamentoResponse(
            agendamento.getId(),
            UsuarioResponse.from(agendamento.getUsuario()),
            BarbeiroResponse.from(agendamento.getBarbeiro()),
            ServicoResponse.from(agendamento.getServico()),
            agendamento.getDataHoraInicio(),
            agendamento.getDataHoraFim(),
            agendamento.getDuracaoEmMinutos(),
            agendamento.getStatus(),
            agendamento.getCriadoEm()
        );
    }
}