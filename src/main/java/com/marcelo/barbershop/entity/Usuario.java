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

/**
 * Representa um usuário do sistema de barbearia.
 * 
 * Um usuário pode assumir diferentes papéis (cliente, barbeiro ou administrador),
 * definidos pelo atributo {@code role}.
 * 
 * Essa entidade contém informações de identificação, autenticação e controle de status.
 */
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

    /**
     * Email único do usuário.
     * Utilizado como identificador para login no sistema.
     */
    @Email
    @NotBlank(message = "Email é obrigatório")
    @Column (unique = true, nullable = false)
    private String email;

    /**
     * Telefone do usuário contendo apenas números.
     * Deve possuir entre 10 e 11 dígitos (com DDD).
     */
    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter apenas números e ter entre 10 e 11 dígitos")  
    @Column(nullable = false)
    private String telefone;

    /**
     * Hash da senha do usuário.
     * Nunca deve ser exposto em respostas da API.
     */
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

    /**
     * Data da última atualização do registro.
     * Atualizada automaticamente a cada modificação.
     */
    @Column(nullable = false)
    private Instant atualizadoEm;


    // =========================
    // Métodos de ciclo de vida
    // =========================

    /**
     * Define automaticamente as datas de criação e atualização
     * antes de persistir a entidade no banco.
     * * Também normaliza o telefone removendo caracteres não numéricos
     * (ex: "(62) 9 8888-7777" vira "62988887777").
     */
    @PrePersist
    public void prePersist() {
        normalizarTelefone();
        if (this.criadoEm == null) {
            this.criadoEm = Instant.now();
        }
        
        this.atualizadoEm = Instant.now();
    }

    /**
     * Atualiza automaticamente a data de modificação
     * antes de atualizar a entidade no banco.
     * Também re-normaliza o telefone caso tenha sido alterado.
     */
    @PreUpdate
    public void preUpdate() {
        normalizarTelefone();
        this.atualizadoEm = Instant.now();
    }

    private void normalizarTelefone() {
        if (this.telefone != null) {
            this.telefone = this.telefone.replace("\\D", "");
        }
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
