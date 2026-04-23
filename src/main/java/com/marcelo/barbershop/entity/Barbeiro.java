package com.marcelo.barbershop.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um barbeiro no sistema.
 * 
 * Um barbeiro está vinculado a um usuário e possui especialidades e serviços
 * que pode executar. Também possui controle de disponibilidade para atendimento.
 */

@Entity
@Table(
    name = "barbeiros",
    indexes = {
        @Index(name = "idx_barbeiro_usuario", columnList = "usuario_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    /**
     * Usuário associado ao barbeiro.
     * Contém dados como nome, email e autenticação.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    @Column(unique = true, nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade = Especialidade.CORTE;

    @Column(nullable = false)
    private Boolean ativo = true;

    /**
     * Serviços que o barbeiro está apto a realizar.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "barbeiro_servico",
        joinColumns = @JoinColumn(name = "barbeiro_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicos = new HashSet<>();

    // =========================
    // Regras de negócio
    // =========================

    /**
     * Ativa e Desativa o barbeiro para atendimento.
     */
    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    public boolean temServicos() {
        return this.servicos != null && !this.servicos.isEmpty();
    }

    /**
     * Verifica se o barbeiro realiza um determinado serviço.
     */
    public boolean atendeServico(Servico servico) {
        return this.servicos.contains(servico);
    }

    public void addServico(Servico servico) {
        this.servicos.add(servico);
    }

    public void removeServico(Servico servico) {
        this.servicos.remove(servico);
    }

    /**
     * Verifica se o barbeiro pode receber agendamentos.
     */
    public boolean podeReceberAgendamento() {
        return Boolean.TRUE.equals(getAtivo()) && temServicos();
    }
}
