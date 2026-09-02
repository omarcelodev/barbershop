# Documentação técnica — Barbershop

Esta documentação descreve o estado observado no código. Ela distingue funcionalidades internas da API HTTP realmente exposta.

## Trilha rápida

1. [Visão geral](01-overview/project-overview.md)
2. [Arquitetura](01-overview/architecture.md)
3. [Domínio](02-domain/domain-model.md)
4. [API](05-api/endpoints.md)
5. [Segurança](06-security/current-security.md)
6. [Dívida técnica](08-analysis/technical-debt.md)
7. [Roadmap](08-analysis/improvement-roadmap.md)

## Índice completo

- [Stack tecnológica](01-overview/tech-stack.md)
- [Regras de negócio e estados](02-domain/business-rules.md)
- [Controllers, services e repositories](03-backend/layers.md)
- [DTOs e mapeamento](03-backend/dto-mapping.md)
- [Tratamento de erros](03-backend/error-handling.md)
- [Modelo físico e migrations](04-database/database-model.md)
- [Autenticação](05-api/authentication.md)
- [Exemplos de requisição](05-api/request-examples.md)
- [Riscos de segurança](06-security/security-risks.md)
- [Setup e execução](07-development/setup.md)
- [Configuração](07-development/configuration.md)
- [Testes](07-development/testing.md)
- [Qualidade e performance](08-analysis/code-quality-performance.md)
- Diagramas: [arquitetura](diagrams/architecture.md), [banco](diagrams/database.md), [fluxos](diagrams/request-flows.md)

## Convenções

- **Implementado**: há código executável correspondente.
- **Disponível internamente**: há um método Java, não necessariamente um endpoint.
- **Recomendação**: proposta futura, nunca funcionalidade atual.
- Quando faltar evidência: **Não foi possível determinar com segurança a partir da implementação atual.**
