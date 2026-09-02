package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Agenda;
import com.marcelo.barbershop.entity.DiaSemana;
import java.time.LocalTime;

public record AgendaResponse(
    Long id,
    Long barbeiroId,
    DiaSemana diaSemana,
    LocalTime horaInicio,
    LocalTime horaFim,
    long duracaoEmMinutos
) {
    public static AgendaResponse from(Agenda agenda) {
        return new AgendaResponse(
            agenda.getId(),
            agenda.getBarbeiro().getId(),
            agenda.getDiaSemana(),
            agenda.getHoraInicio(),
            agenda.getHoraFim(),
            agenda.getDuracaoEmMinutos()
        );
    }
}