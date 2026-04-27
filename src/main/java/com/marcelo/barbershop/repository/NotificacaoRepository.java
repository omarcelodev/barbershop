package com.marcelo.barbershop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcelo.barbershop.entity.Notificacao;
import com.marcelo.barbershop.entity.StatusNotificacao;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findALLByAgendamentoId(Long agendamentoId);

    List<Notificacao> findAllByStatus(StatusNotificacao status);

    List<Notificacao> findAllByStatusOrderByCriadoEmAsc(StatusNotificacao status);
    
}