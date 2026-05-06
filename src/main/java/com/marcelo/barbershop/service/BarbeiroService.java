package com.marcelo.barbershop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.marcelo.barbershop.repository.BarbeiroRepository;
import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.Servico;
import com.marcelo.barbershop.entity.Usuario;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class BarbeiroService {
    private final BarbeiroRepository barbeiroRepository;
    private final UsuarioService usuarioService;
    private final ServicoService servicoService;

    public BarbeiroService(BarbeiroRepository barbeiroRepository, UsuarioService usuarioService, ServicoService servicoService) {
        this.barbeiroRepository = barbeiroRepository;
        this.usuarioService = usuarioService;
        this.servicoService = servicoService;
    }

    public Barbeiro buscarPorId(Long id) {
        return barbeiroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Barbeiro não encontrado: " + id));
    }
    
    public List<Barbeiro> listarAtivos() {
        return barbeiroRepository.findAllByAtivo(true);
    }

    public List<Barbeiro> listarAptosPorServico(Long servicoId) {
        return barbeiroRepository.findAtivosComServico(servicoId);
    }

    @Transactional
    public Barbeiro criar(Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        if(barbeiroRepository.existsByUsuario(usuario)) {
            throw new IllegalArgumentException("Usuário já é barbeiro: " + usuarioId);
        }

        Barbeiro barbeiro = new Barbeiro();
        barbeiro.setUsuario(usuario);
        return barbeiroRepository.save(barbeiro);
    }

    @Transactional
    public void adicionarServico(Long barbeiroId, Long servicoId) {
        Barbeiro barbeiro = buscarPorId(barbeiroId);
        Servico servico = servicoService.buscarPorId(servicoId);
        barbeiro.addServico(servico);
        barbeiroRepository.save(barbeiro);
    }

    @Transactional
    public void removerServico(Long barbeiroId, Long servicoId) {
        Barbeiro barbeiro = buscarPorId(barbeiroId);
        Servico servico = servicoService.buscarPorId(servicoId);
        barbeiro.removeServico(servico);
        barbeiroRepository.save(barbeiro);
    }

    @Transactional
    public void desativar(Long id) {
        Barbeiro barbeiro = buscarPorId(id);
        barbeiro.desativar();
        barbeiroRepository.save(barbeiro);
    }

    @Transactional
    public void ativar(Long id) {
        Barbeiro barbeiro = buscarPorId(id);
        barbeiro.ativar();
        barbeiroRepository.save(barbeiro);
    }
}
