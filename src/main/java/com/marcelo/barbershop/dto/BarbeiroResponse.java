package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.Especialidade;

import java.util.Set;
import java.util.stream.Collectors;

public record BarbeiroResponse(
    Long id,
    UsuarioResponse usuario,
    Especialidade especialidade,
    Boolean ativo,
    Set<ServicoResponse> servicos
) {
    public static BarbeiroResponse from(Barbeiro barbeiro) {
        return new BarbeiroResponse(
            barbeiro.getId(),
            UsuarioResponse.from(barbeiro.getUsuario()),
            barbeiro.getEspecialidade(),
            barbeiro.getAtivo(),
            barbeiro.getServicos().stream()
                .map(ServicoResponse::from)
                .collect(Collectors.toSet())
        );
    }
}