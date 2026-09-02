# Visão geral do projeto

[← Índice](../README.md) · [Arquitetura →](architecture.md)

## Propósito

O projeto é um backend em construção para organizar o domínio de uma barbearia: pessoas usuárias, profissionais, catálogo de serviços, disponibilidade semanal, agendamentos e registros de notificação. O código concentra persistência e regras em uma aplicação Spring Boot conectada a PostgreSQL.

O problema central modelado é combinar cliente, barbeiro, serviço e intervalo de tempo, evitando sobreposição de horários e registrando o ciclo do atendimento. Contudo, a implementação ainda não oferece uma API de negócio: via HTTP existe somente `GET /`, uma resposta simples de disponibilidade.

## Estado observado

| Área | Estado |
|---|---|
| Modelo JPA | Seis entidades e seis enums |
| Persistência | Seis repositories, consultas derivadas e JPQL |
| Casos de uso | Services de usuário, barbeiro, serviço, agenda, agendamento e notificação |
| HTTP | Apenas `TestController` com `GET /` |
| DTOs | Request/response de serviço, sem consumidor |
| Segurança | BCrypt disponível; HTTP integralmente liberado; sem autenticação |
| Banco | PostgreSQL; schema apenas validado; sem migration versionada |
| Testes | Um teste de contexto, sem testes das regras |
| Integrações | Nenhum provedor de e-mail, SMS ou WhatsApp |

## Módulos internos

- **Usuários:** criação com hash de senha, consultas, atualização de nome/telefone e ativação.
- **Barbeiros:** vínculo com usuário, ativação, associação a serviços e consulta de profissionais aptos.
- **Serviços:** catálogo com nome, preço e duração.
- **Agenda:** janelas semanais por barbeiro e dia.
- **Agendamentos:** cálculo de fim, consulta de conflito, reagendamento, cancelamento e conclusão.
- **Notificações:** registro pendente e mudança manual para enviado/falhou; sem entrega externa.

Esses módulos são internos. Exceto pelo endpoint raiz, nenhum está exposto por controller.

## Limitações centrais

É um esqueleto de domínio/persistência, não uma API pronta para produção. Há boas decisões estruturais — camadas, transações read-only, relações lazy, BCrypt, DTOs e lock otimista — mas há lacunas funcionais. A criação de agendamento não atribui o barbeiro à entidade antes de salvar, não consulta a agenda semanal e não garante exclusão mútua entre duas inserções concorrentes.

Não foi possível determinar com segurança a partir da implementação atual como o schema deve ser criado, como usuários autenticariam, quem poderia executar cada operação ou como notificações seriam entregues.

```text
Cliente HTTP -> TestController

Chamador Java futuro -> Service -> Repository -> JPA/Hibernate -> PostgreSQL
                          +-> outro Service para resolver entidades relacionadas
```

Veja [fluxos](../diagrams/request-flows.md) e [roadmap](../08-analysis/improvement-roadmap.md).
