package com.marcelo.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcelo.barbershop.entity.Usuario;
import com.marcelo.barbershop.entity.Role;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findAllByAtivo(Boolean ativo);

    List<Usuario> findAllByRole(Role role);
    
}
