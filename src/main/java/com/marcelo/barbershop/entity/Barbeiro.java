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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "barbeiro_servico",
        joinColumns = @JoinColumn(name = "barbeiro_id"),
        inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private Set<Servico> servicos = new HashSet<>();

// Métodos da entidade
    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}
