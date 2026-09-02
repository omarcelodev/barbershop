package com.marcelo.barbershop.dto;

import com.marcelo.barbershop.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UsuarioRequest(

    @NotBlank(message = "Nome é obrigatório")
    String nome,

    @Email
    @NotBlank(message = "Email é obrigatório")
    String email,

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números e ter entre 10 e 11 dígitos")
    String telefone,

    @NotBlank(message = "Senha é obrigatória")
    String senha,

    Role role
) {}