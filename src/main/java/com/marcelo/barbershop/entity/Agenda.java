package com.marcelo.barbershop.entity;

import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um período de disponibilidade de um barbeiro em um dia da semana.
 * 
 * Cada registro define um intervalo fixo de horário (ex: 08:00 às 18:00)
 * em um dia específico, servindo como base para geração de horários disponíveis
 * para agendamento.
 */

@Entity
@Table(name = "agenda")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Barbeiro associado a esta agenda.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbeiro_id", nullable = false, updatable = false)
    private Barbeiro barbeiro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiaSemana diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;
    
    // =========================
    // Validações de domínio
    // =========================

    /**
     * Garante que o horário de início seja anterior ao horário de fim.
     */
    @PrePersist
    @PreUpdate
    private void validarHorario() {
        if (horaInicio != null && horaFim != null && !horaInicio.isBefore(horaFim)) {
            throw new IllegalStateException("Hora de início deve ser anterior à hora de fim");
        }
    }

    // =========================
    // Regras de domínio simples
    // =========================

    /**
     * Verifica se um horário está dentro do intervalo da agenda.
     */
    public boolean contemHorario(LocalTime horario) {
        return (horario.equals(horaInicio) || horario.isAfter(horaInicio)) && horario.isBefore(horaFim);
    }

    /**
     * Calcula a duração total da jornada em minutos.
     */
    public long getDuracaoEmMinutos() {
        return java.time.Duration.between(horaInicio, horaFim).toMinutes();
    }
}
