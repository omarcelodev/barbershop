package com.marcelo.barbershop.entity;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Email
    @NotBlank(message = "Email é obrigatório")
    @Column (unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números e ter entre 10 e 11 dígitos")  
    @Column(nullable = false)
    private String telefone;

    @JsonIgnore
    @Column(nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CLIENTE;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false, updatable = false)
    private Instant criadoEm;

    @Column(nullable = false)
    private Instant atualizadoEm;


// Métodos da entidade
    @PrePersist
    public void prePersist() {
        if (this.criadoEm == null) {
            this.criadoEm = Instant.now();
        }
        
        this.atualizadoEm = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = Instant.now();
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isCliente() {
        return this.role == Role.CLIENTE;
    }

    public boolean isBarbeiro() {
        return this.role == Role.BARBEIRO;
    }
}
