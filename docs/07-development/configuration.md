# Configuração

[← Setup](setup.md)

## Propriedades atuais

`src/main/resources/application.properties` define nome da aplicação, URL/usuário/senha PostgreSQL, `ddl-auto=validate`, exibição e formatação de SQL. Não existem profiles específicos.

## Configuração recomendada por ambiente

O Spring aceita placeholders com variáveis. Exemplo conceitual para futura alteração de código/configuração — **não aplicado nesta etapa**:

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.show-sql=${JPA_SHOW_SQL:false}
```

Exemplo de ambiente, sem segredos reais:

```env
DATABASE_URL=<your-database-url>
DATABASE_USERNAME=<your-database-user>
DATABASE_PASSWORD=<your-database-password>
```

Não existe `JWT_SECRET`, API key de canal ou configuração de CORS porque essas funcionalidades não estão implementadas.

## Profiles recomendados

- `dev`: banco local e logs convenientes.
- `test`: banco isolado/container e schema migrado.
- `prod`: secrets externos, SQL oculto, erros sanitizados e schema gerido por migration.

Antes de criar profiles, deve-se decidir a fonte oficial do schema e rotacionar a senha atualmente versionada caso tenha valor fora de ambiente descartável.
