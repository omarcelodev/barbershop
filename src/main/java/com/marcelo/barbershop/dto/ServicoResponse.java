package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Servico;

public record ServicoResponse(
    Long id,
    String nome,
    java.math.BigDecimal preco,
    Integer duracao,
    boolean rapido
) {
    public static ServicoResponse from(Servico servico) {
        return new ServicoResponse(
            servico.getId(),
            servico.getNome(),
            servico.getPreco(),
            servico.getDuracao(),
            servico.isRapido()
        );
    }
}