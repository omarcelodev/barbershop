package com.marcelo.barbershop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.marcelo.barbershop.entity.Agenda;
import com.marcelo.barbershop.entity.DiaSemana;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {@link Agenda}.
 *
 * Gerencia informações relacionadas à disponibilidade de barbeiros,
 * permitindo consultas por barbeiro e dia da semana.
 */
public interface AgendaRepository extends JpaRepository <Agenda, Long> {

    /**
     * Retorna todas as agendas associadas a um barbeiro específico.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @return Lista de agendas vinculadas ao barbeiro informado.
     */
    List<Agenda> findAllByBarbeiroId(Long barbeiroId);

    /**
     * Retorna as agendas de um barbeiro filtradas por dia da semana.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @param diaSemana Dia da semana (ex: SEGUNDA, TERCA, etc).
     * @return Lista de agendas que correspondem ao barbeiro e dia informados.
     */
    List<Agenda> findAllByBarbeiroIdAndDiaSemana(Long barbeiroId, DiaSemana diaSemana);

    /**
     * Verifica se já existe uma agenda cadastrada para um barbeiro em um determinado dia da semana.
     *
     * @param barbeiroId Identificador do barbeiro.
     * @param diaSemana Dia da semana a ser verificado.
     * @return {@code true} se já existir uma agenda para o barbeiro no dia informado,
     *         {@code false} caso contrário.
     */
    boolean existsByBarbeiroIdAndDiaSemana(Long barbeiroId, DiaSemana diaSemana);
}