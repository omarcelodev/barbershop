package com.marcelo.barbershop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ServicoRequest(

    @NotBlank(message = "Nome do serviço é obrigatório")
    String nome,

    @NotNull
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    BigDecimal preco,

    @NotNull
    @Min(value = 1, message = "Duração mínima é 1 minuto")
    Integer duracao
) {}