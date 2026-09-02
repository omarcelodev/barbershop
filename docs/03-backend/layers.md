# Camadas do backend

[← Índice](../README.md) · [Arquitetura](../01-overview/architecture.md)

## Controllers

### `TestController`

Único controller. `home()` atende `GET /`, não recebe parâmetros/body, não usa service e retorna texto puro `API rodando!` com status 200 quando executado normalmente. Não é um health check operacional completo: não confirma banco nem dependências.

Não existem controllers para usuário, barbeiro, serviço, agenda, agendamento ou notificação. Logo, os métodos abaixo não são endpoints.

## Services

Todos usam injeção por construtor e `@Transactional(readOnly = true)` na classe; escritas sobrescrevem com `@Transactional`.

### `UsuarioService`

Centraliza identidade persistida para que hash, duplicidade e ativação não fiquem em futuros controllers. Lista/busca usuários, cria com BCrypt, atualiza nome/telefone e ativa/desativa. Depende de `UsuarioRepository` e `PasswordEncoder`.

### `ServicoService`

Mantém o catálogo e sua regra de unicidade na criação. Lista, busca, cria, atualiza e exclui. Atualização não antecipa conflito de nome; exclusão pode encontrar referências no banco.

### `BarbeiroService`

Orquestra o perfil profissional: resolve o usuário, impede duplicidade no cadastro, associa serviços e controla ativação. Depende de `UsuarioService`, `ServicoService` e repository. Não sincroniza `Usuario.role`/`Usuario.ativo` com `Barbeiro.ativo`.

### `AgendaService`

Mantém disponibilidade recorrente e associação bidirecional. Na criação, resolve o barbeiro e impede duplicidade de dia de forma consultiva. Atualização/deleção operam diretamente no repository; atualização não valida duplicidade antecipadamente.

### `AgendamentoService`

É a principal orquestração de domínio: resolve relações, testa elegibilidade/serviço, calcula intervalo e procura conflitos. Também reage, cancela e conclui. Limitações críticas: criação não chama `setBarbeiro`; agenda semanal não é consultada; e o lock otimista não protege duas novas linhas concorrentes.

### `NotificacaoService`

Registra e consulta notificações e muda seu estado. A existência da camada evita acoplar persistência a um futuro provedor, porém hoje não envia mensagens. `buscarPorId` é privado, diferentemente dos demais services.

## Repositories

| Repository | Entidade | Consultas relevantes |
|---|---|---|
| `UsuarioRepository` | `Usuario` | por e-mail, ativo e role; existência por e-mail |
| `BarbeiroRepository` | `Barbeiro` | por usuário, ativo e JPQL de ativos com serviço |
| `ServicoRepository` | `Servico` | por nome e existência por nome |
| `AgendaRepository` | `Agenda` | por barbeiro/dia e existência por combinação |
| `AgendamentoRepository` | `Agendamento` | por usuário/barbeiro/status; conflito; agenda diária ordenada |
| `NotificacaoRepository` | `Notificacao` | por agendamento/status; falhas por criação |

Todas as listagens retornam `List` sem paginação. Relações lazy ajudam nas consultas simples, mas a serialização direta ou acesso iterativo futuro pode causar `LazyInitializationException` ou N+1. Não há `EntityGraph`, fetch join, projections, locking query ou query nativa.

`findConflitantes` implementa corretamente a geometria de sobreposição, mas o padrão `consultar -> inserir` está sujeito a race condition. Índices declarados ajudam FKs e início do agendamento; não há índice composto declarado alinhado a barbeiro + status + intervalo.
