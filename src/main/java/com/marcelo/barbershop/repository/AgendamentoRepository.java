package com.marcelo.barbershop.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marcelo.barbershop.entity.Agendamento;
import com.marcelo.barbershop.entity.Status;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {@link Agendamento}.
 *
 * Contém consultas relacionadas a usuários, barbeiros e validação de conflitos
 * de horários, sendo peça central na lógica de agendamento do sistema.
 */
public interface AgendamentoRepository extends JpaRepository <Agendamento, Long>{

    /**
     * Retorna todos os agendamentos de um usuário específico.
     *
     * @param usuarioId Identificador do usuário.
     * @return Lista de agendamentos vinculados ao usuário.
     */
    List<Agendamento> findAllByUsuarioId(Long usuarioId);

    /**
     * Retorna todos os agendamentos de um barbeiro específico.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @return Lista de agendamentos vinculados ao barbeiro.
     */
    List<Agendamento> findAllByBarbeiroId(Long barbeiroId);

    /**
     * Retorna os agendamentos de um barbeiro filtrados por status.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @param status Status do agendamento (ex: AGENDADO, CANCELADO, CONCLUIDO).
     * @return Lista de agendamentos conforme o barbeiro e status informados.
     */
    List<Agendamento> findAllByBarbeiroIdAndStatus(Long barbeiroId, Status status);

    /**
     * Busca agendamentos que conflitam com um intervalo de tempo específico.
     *
     * Um conflito ocorre quando:
     * - O horário de início de um agendamento existente é antes do fim do novo
     * - E o horário de fim é depois do início do novo
     *
     * @param barbeiroId Identificador do barbeiro.
     * @param inicio Horário de início do novo agendamento.
     * @param fim Horário de fim do novo agendamento.
     * @param statusAtivos Lista de status considerados ativos (ex: AGENDADO, CONFIRMADO).
     * @return Lista de agendamentos conflitantes.
     */
    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.barbeiro.id = :barbeiroId
          AND a.status IN :statusAtivos
          AND a.dataHoraInicio < :fim
          AND a.dataHoraFim > :inicio
    """)
    List<Agendamento> findConflitantes(Long barbeiroId, LocalDateTime inicio, LocalDateTime fim, List<Status> statusAtivos);
    
    /**
     * Retorna os agendamentos de um barbeiro em um dia específico,
     * ordenados pelo horário de início.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @param inicioDia Início do dia (ex: 00:00).
     * @param fimDia Fim do dia (ex: 23:59).
     * @return Lista ordenada de agendamentos do dia.
     */
    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.barbeiro.id = :barbeiroId
          AND a.dataHoraInicio >= :inicioDia
          AND a.dataHoraInicio < :fimDia
        ORDER BY a.dataHoraInicio
    """)
    List<Agendamento> findByBarbeiroEDia(Long barbeiroId, LocalDateTime inicioDia, LocalDateTime fimDia);

}