package com.marcelo.barbershop.repository;

import com.marcelo.barbershop.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {@link Servico}.
 *
 * Disponibiliza operações padrão de CRUD através do JpaRepository,
 * além de consultas derivadas pelo nome conforme convenções do Spring Data JPA.
 */
public interface ServicoRepository extends JpaRepository<Servico, Long> {
     /**
     * Busca um serviço pelo nome.
     *
     * Parametro nome Nome do serviço a ser buscado.
     * Retorna Um {@link Optional} contendo o serviço caso encontrado,
     *         ou vazio caso não exista.
     */
    Optional<Servico> findByNome(String nome);

    /**
     * Verifica se já existe um serviço cadastrado com o nome informado.
     *
     * Parametro nome Nome do serviço a ser verificado.
     * Retorna {@code true} se já existir um serviço com esse nome,
     *         {@code false} caso contrário.
     */
    boolean existsByNome(String nome);
}
