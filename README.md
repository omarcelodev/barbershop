# Barbershop API

Backend em desenvolvimento para o domínio de uma barbearia, modelando usuários, barbeiros, catálogo de serviços, disponibilidade semanal, agendamentos e registros de notificação.

O projeto atualmente possui modelo JPA, repositories e camada de services. A API HTTP ainda está em estágio inicial: somente `GET /` está exposto; autenticação e endpoints de negócio não foram implementados.

## Estado atual

- Java 25 e Spring Boot 4.0.8.
- PostgreSQL via Spring Data JPA/Hibernate.
- Regras internas para cadastro, ativação, serviços, agenda, conflito de horários e estados de notificação.
- BCrypt no cadastro interno de usuário.
- Spring Security configurado com todas as requisições permitidas.
- Um teste de carregamento do contexto; sem suíte de regras/integração.
- Sem migrations, Docker, OpenAPI ou integração externa de notificações.

O projeto **não está pronto para produção**. Há limitações de integridade, segurança, schema e testes documentadas no roadmap.

## Documentação

Comece pelo [índice completo da documentação](docs/README.md).

- [Visão geral](docs/01-overview/project-overview.md)
- [Arquitetura atual e recomendações](docs/01-overview/architecture.md)
- [Stack tecnológica](docs/01-overview/tech-stack.md)
- [Modelo e regras de domínio](docs/02-domain/domain-model.md)
- [Camadas do backend](docs/03-backend/layers.md)
- [Banco de dados](docs/04-database/database-model.md)
- [API HTTP](docs/05-api/endpoints.md)
- [Segurança](docs/06-security/current-security.md)
- [Setup local](docs/07-development/setup.md)
- [Dívida técnica](docs/08-analysis/technical-debt.md)
- [Roadmap de melhorias](docs/08-analysis/improvement-roadmap.md)
- [Diagramas](docs/diagrams/architecture.md)

## Estrutura resumida

```text
src/main/java/com/marcelo/barbershop/
├── config/       Segurança e beans compartilhados
├── controller/   Entrada HTTP (somente TestController)
├── dto/          DTOs de serviço ainda não conectados
├── entity/       Entidades JPA e enums
├── repository/   Spring Data JPA
└── service/      Casos de uso internos e transações
```

## Execução local

Pré-requisitos: JDK 25, PostgreSQL e Maven/Maven Wrapper. O schema precisa existir previamente porque `ddl-auto=validate` não cria tabelas. O repositório ainda não fornece migrations.

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Ao iniciar normalmente, `GET http://localhost:8080/` retorna:

```text
API rodando!
```

As credenciais atualmente presentes em `application.properties` devem ser tratadas como configuração local e externalizadas antes de qualquer uso compartilhado/produção. Consulte [configuração](docs/07-development/configuration.md) e [riscos de segurança](docs/06-security/security-risks.md).

## Testes

```powershell
.\mvnw.cmd test
```

Durante a análise documental, o wrapper Windows falhou antes de iniciar o Maven e não havia Maven global. Veja o resultado e a estratégia recomendada em [testes](docs/07-development/testing.md).

## Licença

Distribuído sob a [licença MIT](LICENSE).

© 2026 Marcelo Gomes
