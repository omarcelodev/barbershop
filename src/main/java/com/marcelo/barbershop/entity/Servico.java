package com.marcelo.barbershop.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 

/**
 * Representa um serviço oferecido pela barbearia.
 * 
 * Um serviço define o que será realizado (ex: corte, barba),
 * incluindo seu preço e duração estimada.
 * 
 * Essas informações são utilizadas no agendamento para cálculo
 * de tempo e valor total.
 */
@Entity
@Table(name = "servicos")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    
    @NotBlank(message = "Nome do serviço é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull
    @Min(value = 1, message = "Duração do serviço é obrigatória")
    @Column(nullable = false)
    private Integer duracao;
}
