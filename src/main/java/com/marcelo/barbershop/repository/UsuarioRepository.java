package com.marcelo.barbershop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.marcelo.barbershop.entity.Usuario;
import com.marcelo.barbershop.entity.Role;
import java.util.Optional;
import java.util.List;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {Usuario}.
 * 
 * Fornece métodos padrão de CRUD através do JpaRepository, além de consultas
 * personalizadas
 */

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Long> {

    /**
     * Busca um usuário pelo e-mail.
     *
     * Parametro email E-mail do usuário a ser buscado.
     * Retorna um {Optional} contendo o usuário caso encontrado,
     *         ou vazio caso não exista.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se já existe um usuário cadastrado com o e-mail informado.
     *
     * Parametro email E-mail a ser verificado.
     * Retorna {true} se já existir um usuário com esse e-mail,
     *         {false} caso contrário.
     */
    boolean existsByEmail(String email);

    /**
     * Retorna uma lista de usuários filtrados pelo status de ativação.
     *
     * Parametro ativo Indica se o usuário está ativo (true) ou inativo (false).
     * Retorna uma lista de usuários que correspondem ao status informado.
     */
    List<Usuario> findAllByAtivo(Boolean ativo);

    /**
     * Retorna todos os usuários que possuem um determinado papel (role).
     *
     * Parametro role Papel/permissão do usuário (ex: ADMIN, CLIENTE).
     * Retorna uma lista de usuários associados ao role informado.
     */
    List<Usuario> findAllByRole(Role role);
    
}
