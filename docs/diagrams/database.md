# Diagrama do banco de dados

[← Índice](../README.md) · [Modelo físico](../04-database/database-model.md)

Este ER representa os mapeamentos JPA. O schema físico real não foi fornecido.

```mermaid
erDiagram
    USUARIOS ||--o| BARBEIROS : possui_perfil
    BARBEIROS ||--o{ AGENDA : disponibiliza
    BARBEIROS }o--o{ SERVICOS : realiza
    USUARIOS ||--o{ AGENDAMENTOS : solicita
    BARBEIROS ||--o{ AGENDAMENTOS : atende
    SERVICOS ||--o{ AGENDAMENTOS : define
    AGENDAMENTOS ||--o{ NOTIFICACAO : gera

    USUARIOS {
        bigint id PK
        varchar nome
        varchar email UK
        varchar telefone
        varchar senhaHash
        varchar role
        boolean ativo
        timestamp criadoEm
        timestamp atualizadoEm
    }
    BARBEIROS {
        bigint id PK
        bigint usuario_id FK
        varchar especialidade
        boolean ativo
    }
    SERVICOS {
        bigint id PK
        varchar nome UK
        decimal preco
        integer duracao
    }
    AGENDA {
        bigint id PK
        bigint barbeiro_id FK
        varchar diaSemana
        time horaInicio
        time horaFim
    }
    AGENDAMENTOS {
        bigint id PK
        bigint usuario_id FK
        bigint barbeiro_id FK
        bigint servico_id FK
        timestamp dataHoraInicio
        timestamp dataHoraFim
        varchar status
        bigint version
        timestamp criadoEm
        timestamp atualizadoEm
    }
    NOTIFICACAO {
        bigint id PK
        bigint agendamento_id FK
        varchar canal
        text mensagem
        timestamp criadoEm
        timestamp enviadoEm
        varchar status
    }
```

Observação: camelCase acima acompanha atributos para legibilidade; nomes físicos de colunas implícitas dependem da naming strategy. `barbeiro_servico` é a join table N:N e foi omitida como entidade associativa porque não possui classe própria.
