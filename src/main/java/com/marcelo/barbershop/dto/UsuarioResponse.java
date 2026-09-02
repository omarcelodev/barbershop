package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Role;
import com.marcelo.barbershop.entity.Usuario;
import java.time.Instant;

public record UsuarioResponse(
    Long id,
    String nome,
    String email,
    String telefone,
    Role role,
    Boolean ativo,
    Instant criadoEm
) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTelefone(),
            usuario.getRole(),
            usuario.getAtivo(),
            usuario.getCriadoEm()
        );
    }
}