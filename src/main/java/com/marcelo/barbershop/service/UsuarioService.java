package com.marcelo.barbershop.service;


import com.marcelo.barbershop.entity.Usuario;
import com.marcelo.barbershop.entity.Role;
import com.marcelo.barbershop.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + id));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + email));
    }

    public List<Usuario> listarAtivos() {
        return usuarioRepository.findAllByAtivo(true);
    }

    public List<Usuario> listarPorRole(Role role) {
        return usuarioRepository.findAllByRole(role);
    }

    @Transactional
    public Usuario criar(Usuario usuario, String senhaPura) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + usuario.getEmail());
        }
        usuario.setSenhaHash(passwordEncoder.encode(senhaPura));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario dados) {
        Usuario existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        existente.setTelefone(dados.getTelefone());
        return usuarioRepository.save(existente);
    }

    @Transactional
    public void desativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.desativar();
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void ativar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.ativar();
        usuarioRepository.save(usuario);
    }
}
