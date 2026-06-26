package com.marcelo.barbershop.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.marcelo.barbershop.entity.Agenda;
import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.DiaSemana;
import com.marcelo.barbershop.repository.AgendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class AgendaService {
    
    private final AgendaRepository agendaRepository;
    private final BarbeiroService barbeiroService;

    public AgendaService(AgendaRepository agendaRepository, BarbeiroService barbeiroService) {
        this.agendaRepository = agendaRepository;
        this.barbeiroService = barbeiroService;
    }

    public List<Agenda> listarPorBarbeiro(Long barbeiroId) {
        return agendaRepository.findAllByBarbeiroId(barbeiroId);
    }

    public List<Agenda> listarPorBarbeiroEDia(Long barbeiroId, DiaSemana dia) {
        return agendaRepository.findAllByBarbeiroIdAndDiaSemana(barbeiroId, dia);
    }
    
    @Transactional
    public Agenda criar(Long barbeiroId, Agenda agenda) {
        Barbeiro barbeiro = barbeiroService.buscarPorId(barbeiroId);

        if (agendaRepository.existsByBarbeiroIdAndDiaSemana(barbeiroId, agenda.getDiaSemana())) {
            throw new IllegalArgumentException(
                "Barbeiro já possui agenda para  " + agenda.getDiaSemana()
            );
        }
        
        barbeiro.addAgenda(agenda);
        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda atualizar(Long id, Agenda dados) {
        Agenda existente = agendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aegnda não encontrada: " + id));

        existente.setHoraInicio(dados.getHoraInicio());
        existente.setHoraFim(dados.getHoraFim());
        existente.setDiaSemana(dados.getDiaSemana());

        return agendaRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        Agenda agenda = agendaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Agenda não encontrada: " + id));

        agendaRepository.delete(agenda);
    }
}
