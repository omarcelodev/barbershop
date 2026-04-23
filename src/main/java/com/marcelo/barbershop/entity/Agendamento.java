package com.marcelo.barbershop.entity;

import java.time.Instant;
import java.time.LocalDateTime;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um agendamento realizado por um cliente com um barbeiro.
 * 
 * Um agendamento vincula um usuário, um barbeiro e um serviço
 * em um intervalo específico de data e hora.
 * 
 * É responsável por representar a ocupação da agenda.
 */
@Entity
@Table(name = "agendamentos",
    indexes = {
        @Index(name = "idx_agendamento_usuario", columnList = "usuario_id"),
        @Index(name = "idx_agendamento_barbeiro", columnList = "barbeiro_id"),
        @Index(name = "idx_agendamento_data_inicio", columnList = "dataHoraInicio")
    } 
)
@Getter
@Setter 
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.Agendado;
    
    @Column(nullable = false, updatable = false)
    private Instant criadoEm;

    // =========================
    // Ciclo de vida JPA
    // =========================

    @PrePersist
    public void prePersist() {
        this.criadoEm = Instant.now();
        validarHorario();
    }

    @PreUpdate
    public void validarHorario() {
        if (dataHoraInicio != null && dataHoraFim != null && !dataHoraInicio.isBefore(dataHoraFim)) {
            throw new IllegalStateException("Início deve ser antes do fim");
        }
    }

    // =========================
    // Regras de domínio simples
    // =========================

    public void definirHorario(LocalDateTime inicio, Servico servico) {
        if (servico == null) throw new IllegalArgumentException("Serviço é obrigatório");
        this.servico = servico;
        this.dataHoraInicio = inicio;
        this.dataHoraFim = inicio.plusMinutes(servico.getDuracao());
    }

    /**
     * Verifica se o agendamento está ativo (não cancelado).
     */
    public boolean isAtivo() {
        return this.status == Status.Agendado || this.status == Status.Confirmado;
    }

    public void cancelar() {
        this.status = Status.Cancelado;
    }

    public boolean isConcluido() {
        return this.status == Status.Concluido;
    }

    public long getDuracaoEmMinutos() {
        return java.time.Duration.between(dataHoraInicio, dataHoraFim).toMinutes();
    }

    /**
     * Verifica se este agendamento conflita com outro.
     */
    public boolean conflitoCom(Agendamento outro) { 
        return this.dataHoraInicio.isBefore(outro.dataHoraFim) && outro.dataHoraInicio.isBefore(this.dataHoraFim);
    }
}