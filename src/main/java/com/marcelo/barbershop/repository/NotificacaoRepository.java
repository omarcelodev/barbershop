package com.marcelo.barbershop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marcelo.barbershop.entity.Notificacao;
import com.marcelo.barbershop.entity.StatusNotificacao;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {@link Notificacao}.
 *
 * Fornece operações de persistência padrão via JpaRepository, além de consultas
 * específicas relacionadas a agendamentos e status de notificações.
 */
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

     /**
     * Retorna todas as notificações associadas a um determinado agendamento.
     *
     * @param agendamentoId Identificador do agendamento.
     * @return Lista de notificações vinculadas ao agendamento informado.
     */
    List<Notificacao> findAllByAgendamentoId(Long agendamentoId);

    /**
     * Retorna todas as notificações filtradas por status.
     *
     * @param status Status da notificação (ex: PENDENTE, ENVIADA, FALHA).
     * @return Lista de notificações que possuem o status informado.
     */
    List<Notificacao> findAllByStatus(StatusNotificacao status);

     /**
     * Retorna notificações filtradas por status, ordenadas pela data de criação
     * em ordem crescente (mais antigas primeiro).
     *
     * @param status Status da notificação.
     * @return Lista ordenada de notificações conforme o status e data de criação.
     */
    List<Notificacao> findAllByStatusOrderByCriadoEmAsc(StatusNotificacao status);
    
}