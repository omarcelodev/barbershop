package com.marcelo.barbershop.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AgendamentoRequest(

    @NotNull(message = "ID do usuário é obrigatório")
    Long usuarioId,

    @NotNull(message = "ID do barbeiro é obrigatório")
    Long barbeiroId,

    @NotNull(message = "ID do serviço é obrigatório")
    Long servicoId,

    @NotNull(message = "Data e hora de início são obrigatórias")
    @Future(message = "O agendamento deve ser para uma data futura")
    LocalDateTime dataHoraInicio
) {}