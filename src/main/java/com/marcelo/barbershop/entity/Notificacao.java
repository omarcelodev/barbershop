package com.marcelo.barbershop.entity;

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
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma notificação enviada ao usuário relacionada a um agendamento.
 *
 * Essa entidade registra tentativas de comunicação com o usuário por diferentes
 * canais (ex: email, SMS, WhatsApp), incluindo o conteúdo da mensagem,
 * status de envio e timestamps de criação e envio.
 *
 * É utilizada para controle de envio, reprocessamento (retry) e auditoria.
 */

@Entity
@Table(name = "notificacao", indexes = {
    @Index(name = "idx_notificacao_agendamento", columnList = "agendamento_id"),
    @Index(name = "idx_notificacao_usuario", columnList = "usuario_id")
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacao {  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Canal canal;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column
    private LocalDateTime enviadoEm;   

    @Column
    private LocalDateTime criadoEm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status = StatusNotificacao.PENDENTE;

    /**
     * Define automaticamente a data de criação antes de persistir a entidade.
     * Garante também que o status inicial seja PENDENTE.
     */
    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
        if (this.status == null) { 
            this.status = StatusNotificacao.PENDENTE;
        }
    }

    /**
     * Verifica se a notificação já foi enviada com sucesso.
     */
    public boolean foiEnviada() {
        return this.status == StatusNotificacao.ENVIADO;
    }

    
    public void marcarComoEnviada() {
        this.status = StatusNotificacao.ENVIADO;
        this.enviadoEm = LocalDateTime.now();
    }

    public void marcarComoFalha(){
        this.status = StatusNotificacao.FALHOU;
    }

    public boolean falhou() {
        return this.status == StatusNotificacao.FALHOU;
    }
}
