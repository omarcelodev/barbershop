# Banco de dados e migrations

[← Índice](../README.md) · [Diagrama ER](../diagrams/database.md)

## Configuração observada

- PostgreSQL em `jdbc:postgresql://localhost:5432/barbershop`.
- Hibernate com `ddl-auto=validate`: valida um schema preexistente, não o cria.
- SQL e formatação habilitados.
- IDs usam `IDENTITY`.
- Não há migrations, DDL ou seed versionados.

Assim, um clone limpo não contém instruções executáveis para criar as tabelas esperadas. Não foi possível determinar com segurança a partir da implementação atual todas as constraints e tipos existentes no banco real.

## Tabelas inferidas dos mapeamentos

| Tabela explícita | PK | FKs/associações | Constraints declaradas relevantes |
|---|---|---|---|
| `usuarios` | `id` | — | e-mail único; campos principais não nulos |
| `barbeiros` | `id` | `usuario_id` | usuário não nulo/imutável; índice no usuário |
| `servicos` | `id` | — | nome único; preço `(10,2)`; não nulos |
| `agenda` | `id` | `barbeiro_id` | FK lógica não nula/imutável; índice no barbeiro |
| `agendamentos` | `id` | usuário, barbeiro, serviço | FKs lógicas não nulas; versão; índices separados |
| `notificacao` | `id` | `agendamento_id` | FK lógica não nula; índices declarados |
| `barbeiro_servico` | composta não declarada | barbeiro, serviço | join table N:N |

Os nomes das colunas não explicitadas dependem da naming strategy do Hibernate/Spring. O índice `idx_notificacao_usuario` referencia `usuario_id`, mas `Notificacao` não possui essa coluna no mapeamento; isso é uma provável incompatibilidade de schema/boot. A entidade só chega ao usuário através de `agendamento`.

## Integridade e modelagem

- A unicidade agenda/barbeiro/dia é apenas verificada em Java, vulnerável a concorrência e atualização.
- Não há constraint declarada que impeça sobreposição de agendamentos.
- `@Version` protege update da mesma linha, não inserts diferentes.
- Alterar preço/duração de serviço não preserva snapshot comercial no agendamento; apenas o intervalo já calculado permanece.
- Timestamps misturam `Instant` (`Usuario`, `Agendamento`) e `LocalDateTime` (`Notificacao`), criando semântica temporal desigual.
- Exclusões de serviço e relacionamentos podem falhar por FKs; a política desejada não está documentada no código.

## Índices e performance

Há índices individuais nas FKs mais acessadas e no início de agendamento. Para a consulta de conflito, um índice composto iniciando por `barbeiro_id` e possivelmente `status` merece medição. A escolha correta depende de volume e plano `EXPLAIN`; não é possível afirmar degradação real sem dados.

## Migrations

Não existem. Recomenda-se introduzir Flyway ou Liquibase, criar uma migration baseline compatível com o banco existente e fazer mudanças futuras somente por arquivos versionados. A ordem exige primeiro inspecionar/exportar o schema real para não presumir que ele coincide integralmente com as anotações.
