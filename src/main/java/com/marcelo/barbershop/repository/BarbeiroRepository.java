package com.marcelo.barbershop.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.Usuario;

public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long>{

    Optional<Barbeiro> findByUsuario(Usuario usuario);

    Optional<Barbeiro> findByUsuarioId(Long usuarioId);

    boolean existsByUsuario(Usuario usuario);

    List<Barbeiro> findAllByAtivo(Boolean ativo);

    @Query("SELECT b FROM Barbeiro b JOIN b.servicos s WHERE s.id = :servicoId AND b.ativo = true")
    List<Barbeiro> findAtivosComServico(Long servicoId);

    
}