package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.DiaSemana;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record AgendaRequest(

    @NotNull(message = "Dia da semana é obrigatório")
    DiaSemana diaSemana,

    @NotNull(message = "Hora de início é obrigatória")
    LocalTime horaInicio,

    @NotNull(message = "Hora de fim é obrigatória")
    LocalTime horaFim
) {}