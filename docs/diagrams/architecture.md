# Diagrama de arquitetura

[← Índice](../README.md) · [Descrição](../01-overview/architecture.md)

Linhas sólidas representam fluxo existente; a linha pontilhada representa a conexão planejada, ainda ausente, entre HTTP e domínio.

```mermaid
flowchart TD
    Client[Cliente HTTP] --> TC[TestController]
    TC --> Text[Resposta texto]

    Future[Controllers de domínio\nnão implementados] -.-> Services

    subgraph Application[Aplicação Spring Boot]
        Security[SecurityFilterChain\npermitAll] --> TC
        Security -.-> Future
        Services[Services transacionais]
        Repositories[Spring Data Repositories]
        Entities[Entidades JPA]
        Services --> Repositories
        Services --> Entities
        Repositories --> Entities
    end

    Repositories --> Hibernate[JPA / Hibernate]
    Hibernate --> PostgreSQL[(PostgreSQL)]
```

Dependências service-to-service relevantes:

```mermaid
flowchart LR
    AgendaService --> BarbeiroService
    BarbeiroService --> UsuarioService
    BarbeiroService --> ServicoService
    AgendamentoService --> UsuarioService
    AgendamentoService --> BarbeiroService
    AgendamentoService --> ServicoService
    NotificacaoService --> AgendamentoService
```
