package com.marcelo.barbershop.dto;

import jakarta.validation.constraints.NotNull;

public record BarbeiroRequest(

    @NotNull(message = "ID do usuário é obrigatório")
    Long usuarioId
) {}