# Testes

[← Setup](setup.md) · [Qualidade](../08-analysis/code-quality-performance.md)

## Estado atual

Há somente `BarbershopApplicationTests.contextLoads()`, anotado com `@SpringBootTest`. Ele tenta carregar o contexto completo e, por causa do datasource real e `ddl-auto=validate`, tende a depender de PostgreSQL/schema externos. Não há configuração de teste dedicada.

Não existem testes unitários, slices MVC/JPA, integração de services, concorrência, segurança nem cobertura configurada.

Nesta análise, `.\mvnw.cmd test` nem chegou ao Maven devido a falha do wrapper Windows. Maven global também não estava instalado. Portanto, resultado da suíte: **não executada**.

## Estratégia recomendada

1. **Domínio unitário:** intervalos, duração, callbacks/normalização e todas as transições de status.
2. **Services unitários:** duplicidades, ausência, ativação, serviço incompatível e verificações de agenda.
3. **Repositories com PostgreSQL real em container:** JPQL de conflito, limites de intervalo, índices/constraints e naming físico.
4. **Integração concorrente:** duas reservas simultâneas para o mesmo barbeiro/intervalo; exatamente uma deve vencer.
5. **MVC contract tests:** validação, JSON, códigos e erros, quando controllers existirem.
6. **Security tests:** público/privado, roles e ownership, quando autenticação existir.
7. **Smoke test:** contexto com profile de teste reproduzível e migrations.

Casos prioritários: criação atual sem `barbeiro`, efeito de `REAGENDADO` na detecção de conflito, conclusão a partir de cancelado, duplicidade de agenda via update e índice inválido de notificação.
