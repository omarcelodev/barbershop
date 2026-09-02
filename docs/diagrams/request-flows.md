# Fluxos principais

[← Índice](../README.md) · [Regras](../02-domain/business-rules.md)

## Requisição HTTP real

```mermaid
sequenceDiagram
    actor Client
    participant Security as SecurityFilterChain
    participant Controller as TestController
    Client->>Security: GET /
    Security->>Security: permitAll
    Security->>Controller: home()
    Controller-->>Client: 200 "API rodando!"
```

## Criação interna de usuário

```mermaid
sequenceDiagram
    participant Caller as Chamador Java futuro
    participant Service as UsuarioService
    participant Repo as UsuarioRepository
    participant BCrypt as PasswordEncoder
    Caller->>Service: criar(usuario, senhaPura)
    Service->>Repo: existsByEmail(email)
    alt já existe
        Service-->>Caller: IllegalArgumentException
    else não existe
        Service->>BCrypt: encode(senhaPura)
        Service->>Repo: save(usuario com hash)
        Repo-->>Service: usuário persistido
        Service-->>Caller: Usuario
    end
```

## Criação interna de agendamento — comportamento atual

```mermaid
sequenceDiagram
    participant Caller
    participant AS as AgendamentoService
    participant US as UsuarioService
    participant BS as BarbeiroService
    participant SS as ServicoService
    participant Repo as AgendamentoRepository
    Caller->>AS: criar(usuarioId, barbeiroId, servicoId, início)
    AS->>US: buscarPorId
    AS->>BS: buscarPorId
    AS->>SS: buscarPorId
    AS->>AS: validar barbeiro ativo/com serviço
    AS->>AS: definir serviço, início e fim
    Note over AS: Agenda semanal não é consultada
    AS->>Repo: findConflitantes
    alt conflito
        AS-->>Caller: IllegalStateException
    else livre
        Note over AS: barbeiro não é atribuído ao Agendamento
        AS->>Repo: save
        Repo-->>Caller: provável erro por barbeiro_id nulo
    end
```

## Notificação — registro, não entrega

```mermaid
flowchart LR
    Caller[Chamador Java futuro] --> NS[NotificacaoService.criar]
    NS --> AS[AgendamentoService.buscarPorId]
    NS --> NR[NotificacaoRepository.save]
    NR --> Pending[Status PENDENTE]
    Pending -. sem worker/provedor .-> External[Email / SMS / WhatsApp]
```

Não existe fluxo real de autenticação, confirmação de agendamento ou envio externo; diagramá-los como existentes seria inventar funcionalidade.
