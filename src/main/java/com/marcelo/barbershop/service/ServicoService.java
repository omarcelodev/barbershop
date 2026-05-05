package com.marcelo.barbershop.service;

import com.marcelo.barbershop.entity.Servico;
import com.marcelo.barbershop.repository.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ServicoService {
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return servicoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Serviço não encontrado: " + id));
    }

    @Transactional
    public Servico criar(Servico servico) {
        if (servicoRepository.existsByNome(servico.getNome())) {
            throw new IllegalArgumentException("Já existe um serviço com o nome: " + servico.getNome());
        }
        return servicoRepository.save(servico);
    }

    @Transactional
    public Servico atualizar(Long id, Servico dados) {
        Servico existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setPreco(dados.getPreco());
        existente.setDuracao(dados.getDuracao());
        return servicoRepository.save(existente);
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }
}
