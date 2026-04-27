package com.marcelo.barbershop.repository;

import com.marcelo.barbershop.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long>{
    Optional<Servico> findByNome(String nome);

    boolean existsByNome(String nome);
    
}
