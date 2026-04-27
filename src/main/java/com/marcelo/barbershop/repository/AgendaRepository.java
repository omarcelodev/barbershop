package com.marcelo.barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcelo.barbershop.entity.Agenda;
import com.marcelo.barbershop.entity.DiaSemana;
public interface AgendaRepository extends JpaRepository <Agenda, Long> {

    List<Agenda> findAllByBarbeiroId(Long barbeiroId);

    List<Agenda> findAllByBarbeiroIdAndDiaSemana(Long barbeiroId, DiaSemana diaSemana);

    boolean existsByBarbeiroIdAndDiaSemana(Long barbeiroId, DiaSemana diaSemana);
}