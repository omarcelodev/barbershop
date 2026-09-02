package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Canal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacaoRequest(

    @NotNull(message = "ID do agendamento é obrigatório")
    Long agendamentoId,

    @NotNull(message = "Canal é obrigatório")
    Canal canal,

    @NotBlank(message = "Mensagem é obrigatória")
    String mensagem
) {}