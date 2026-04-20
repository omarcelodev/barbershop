package com.marcelo.barbershop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo = true;

// Métodos da entidade
    public void ativar() {
    this.ativo = true;
    }

    public void desativar() {
    this.ativo = false;
    }
}
