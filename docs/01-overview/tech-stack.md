# Stack tecnológica

[← Índice](../README.md)

| Tecnologia | Versão/escopo confirmado | Papel |
|---|---|---|
| Java | `25`; runtime local observado `25.0.2` | Linguagem e execução |
| Spring Boot | `4.0.8` | Bootstrap e autoconfiguração |
| Spring Web MVC | starter | HTTP MVC |
| Spring Data JPA / Hibernate | gerenciados pelo parent | ORM e repositories |
| Spring Security | starter | Filtros e BCrypt |
| Bean Validation | starter | Constraints declarativas |
| PostgreSQL JDBC | runtime | Banco relacional |
| Lombok | opcional + processor | Boilerplate |
| Maven Wrapper | Maven `3.9.14` configurado | Build pretendido |
| JUnit / Spring Boot Test | test | Infraestrutura de testes |

Não há Flyway/Liquibase, OpenAPI, Docker, biblioteca JWT, mensageria, SDK de notificação, cache ou cobertura no repositório.

Não foi possível concluir auditoria de CVEs: o wrapper não iniciou e não existe Maven global neste ambiente. Isso não prova presença nem ausência de vulnerabilidades; recomenda-se SCA em CI.
