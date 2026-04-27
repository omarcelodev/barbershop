package com.marcelo.barbershop.repository;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marcelo.barbershop.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository <Agendamento, Long>{

    List<Agendamento> findAllByUsuarioId(Long usuarioId);

    List<Agendamento> findAllByBarbeiroId(Long barbeiroId);

    List<Agendamento> findAllByBarbeiroAndStatus(Long barbeiroId, Status status);

    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.barbeiro.id = :barbeiroId
          AND a.status IN :statusAtivos
          AND a.dataHoraInicio < :fim
          AND a.dataHoraFim > :inicio
    """)
    List<Agendamento> findConflitantes(Long barbeiroId, LocalDateTime inicio, LocalDateTime fim, List<Status> statusAtivos);
    
    @Query("""
        SELECT a FROM Agendamento a
        WHERE a.barbeiro.id = :barbeiroId
          AND a.dataHoraInicio >= inicioDia
          AND a.dataHoraInicio < :fimDia
        ORDER BY a.dataHoraInicio
    """)
    List<Agendamento> findByBarbeiroEDia(Long barbeiroId, LocalDateTime inicioDia, LocalDateTime fimDia);

}