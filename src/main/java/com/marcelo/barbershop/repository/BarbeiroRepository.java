package com.marcelo.barbershop.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.marcelo.barbershop.entity.Barbeiro;
import com.marcelo.barbershop.entity.Usuario;

/**
 * Repositório responsável pelo acesso e manipulação de dados da entidade {@link Barbeiro}.
 *
 * Fornece operações CRUD padrão via JpaRepository e consultas específicas
 * relacionadas ao vínculo com usuário, status de ativação e serviços oferecidos.
 */
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long>{

    /**
     * Busca um barbeiro associado a um usuário específico.
     *
     * @param usuario Usuário vinculado ao barbeiro.
     * @return Um {@link Optional} contendo o barbeiro caso encontrado,
     *         ou vazio caso não exista vínculo.
     */
    Optional<Barbeiro> findByUsuario(Usuario usuario);

    /**
     * Busca um barbeiro a partir do ID do usuário associado.
     *
     * @param usuarioId Identificador do usuário.
     * @return Um {@link Optional} contendo o barbeiro caso encontrado,
     *         ou vazio caso não exista.
     */
    Optional<Barbeiro> findByUsuarioId(Long usuarioId);

    /**
     * Verifica se existe um barbeiro associado a um determinado usuário.
     *
     * @param usuario Usuário a ser verificado.
     * @return {@code true} se existir um barbeiro vinculado ao usuário,
     *         {@code false} caso contrário.
     */
    boolean existsByUsuario(Usuario usuario);

     /**
     * Retorna todos os barbeiros filtrados pelo status de ativação.
     *
     * @param ativo Indica se o barbeiro está ativo (true) ou inativo (false).
     * @return Lista de barbeiros conforme o status informado.
     */
    List<Barbeiro> findAllByAtivo(Boolean ativo);

    /**
     * Retorna todos os barbeiros ativos que realizam um determinado serviço.
     *
     * A consulta utiliza JOIN com a coleção de serviços do barbeiro.
     *
     * @param servicoId Identificador do serviço.
     * @return Lista de barbeiros ativos que oferecem o serviço informado.
     */
    @Query("SELECT b FROM Barbeiro b JOIN b.servicos s WHERE s.id = :servicoId AND b.ativo = true")
    List<Barbeiro> findAtivosComServico(Long servicoId);

    
}