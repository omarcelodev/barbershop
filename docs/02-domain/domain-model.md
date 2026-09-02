# Modelo de domínio

[← Índice](../README.md) · [Regras →](business-rules.md) · [ER](../diagrams/database.md)

## Entidades

### `Usuario`

Identidade e dados compartilhados por clientes, barbeiros e administradores. `role` inicia em `CLIENTE`, `ativo` em `true`, e callbacks preenchem timestamps. A senha fica em `senhaHash`, com `@JsonIgnore`. O telefone é normalizado antes da persistência/atualização. Ainda não há autenticação consumindo esses dados.

### `Barbeiro`

Perfil profissional vinculado a um `Usuario`, com uma `Especialidade`, estado ativo, serviços realizáveis e agendas semanais. Elegibilidade básica exige ativo e ao menos um serviço; o serviço solicitado também é conferido. Criar barbeiro não altera o role do usuário para `BARBEIRO`; não foi possível determinar se isso é intencional.

### `Servico`

Catálogo com nome único, preço positivo e duração mínima. A duração calcula o fim do agendamento. `isRapido()` classifica até 30 minutos e aparece apenas no DTO de resposta.

### `Agenda`

Intervalo recorrente por barbeiro/dia. Callback impede início igual ou posterior ao fim. O service tenta manter uma agenda por dia na criação. A agenda não é consultada ao criar/reagendar agendamentos: registra disponibilidade, mas não a impõe.

### `Agendamento`

Liga usuário, barbeiro e serviço a um intervalo. O fim é derivado de `início + duração`. Status padrão `AGENDADO`; timestamps usam `Instant`; `@Version` controla atualizações concorrentes da mesma linha. Em `criar`, o barbeiro é validado, mas não atribuído antes de `save`, apesar da FK não nula.

### `Notificacao`

Registra mensagem, canal, estado e momento de envio de um agendamento. É uma outbox conceitual, mas não há dispatcher, retry automático ou integração externa.

## Relacionamentos JPA

| Origem | Cardinalidade | Destino | Detalhe |
|---|---:|---|---|
| `Usuario` | 1 : 0..1 | `Barbeiro` | FK em barbeiro |
| `Barbeiro` | N : N | `Servico` | `barbeiro_servico` |
| `Barbeiro` | 1 : N | `Agenda` | cascade + orphan removal |
| `Usuario` | 1 : N implícito | `Agendamento` | somente lado N |
| `Barbeiro` | 1 : N implícito | `Agendamento` | somente lado N |
| `Servico` | 1 : N implícito | `Agendamento` | somente lado N |
| `Agendamento` | 1 : N implícito | `Notificacao` | somente lado N |

## Enums

- `Role`: `CLIENTE`, `BARBEIRO`, `ADMIN`.
- `Especialidade`: `CORTE`, `BARBA`, `SOMBRACELHA` (grafia do código).
- `DiaSemana`: `SEGUNDA` a `DOMINGO`.
- `Status`: `AGENDADO`, `CONFIRMADO`, `CANCELADO`, `CONCLUIDO`, `REAGENDADO`.
- `Canal`: `WHATSAPP`, `SMS`, `EMAIL`.
- `StatusNotificacao`: `PENDENTE`, `ENVIADO`, `FALHOU`.

O schema real não está versionado; o diagrama descreve mapeamentos JPA, não um banco inspecionado.
