# Arquitetura

[← Visão geral](project-overview.md) · [Diagrama](../diagrams/architecture.md)

## Arquitetura atual

A aplicação é um monólito em camadas sob `com.marcelo.barbershop`:

| Package | Responsabilidade real |
|---|---|
| `config` | Cadeia Spring Security e beans de autenticação/hash |
| `controller` | Entrada HTTP; hoje somente o endpoint raiz |
| `dto` | Contratos de serviço ainda não conectados à web |
| `entity` | Modelo persistido, enums, callbacks e regras locais |
| `repository` | CRUD Spring Data e consultas |
| `service` | Transações, resolução de dependências e decisões de negócio |

O fluxo pretendido é `Controller -> Service -> Repository -> Database`, mas não ocorre para o domínio via HTTP. Services chamam outros services; `AgendamentoService`, por exemplo, resolve usuário, barbeiro e serviço antes de persistir.

### Dependências

- Controllers não dependem de services atualmente.
- Services dependem de repositories e, em quatro casos, de outros services.
- Repositories dependem das entidades JPA.
- Entidades misturam persistência, validação e, em `Usuario`, serialização Jackson.
- Não há interfaces próprias de casos de uso, módulos separados, eventos, filas ou adaptadores externos.

### Padrões identificados

- arquitetura em camadas;
- Repository via Spring Data;
- Service Layer transacional;
- regras simples dentro de entidades, embora setters públicos possam contorná-las;
- DTO/assembler em `ServicoResponse.from`, ainda desconectado;
- optimistic locking em `Agendamento`, válido para a mesma linha, não para duas inserções sobrepostas.

`@Transactional(readOnly = true)` nos services, sobrescrito nos métodos de escrita, revela intenção de explicitar fronteiras transacionais. Relações lazy reduzem carregamento automático. Enums em texto evitam dependência da ordem ordinal.

## Recomendações

As mudanças abaixo **não existem atualmente**:

1. Criar uma camada HTTP com DTOs, validação e mapeamento, sem serializar entidades JPA.
2. Definir casos de uso e autorização por recurso antes de expor endpoints.
3. Consolidar invariantes de agendamento — agenda, transições e concorrência — numa fronteira única.
4. Adotar migrations como fonte do schema e constraints de integridade.
5. Reduzir acoplamento service-to-service onde uma leitura direta ou caso de uso explícito for mais claro.
6. Introduzir erros padronizados, observabilidade e testes por camada.

Veja [dívida técnica](../08-analysis/technical-debt.md).
