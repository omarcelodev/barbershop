# Segurança existente

[← Índice](../README.md) · [Riscos →](security-risks.md)

## Controles confirmados

- `UsuarioService` usa `BCryptPasswordEncoder`; senha pura não é atribuída à entidade.
- `senhaHash` possui `@JsonIgnore`, reduzindo exposição em serialização Jackson direta.
- Bean Validation declara formato de e-mail/telefone e limites de preço/duração.
- Queries usam JPQL parametrizada e métodos derivados, sem concatenação de entrada; não foi encontrado vetor evidente de SQL injection.
- FKs JPA são declaradas não nulas nos vínculos essenciais.
- `@Version` reduz lost updates ao modificar o mesmo agendamento.
- Toda rota atual é pública por configuração explícita, coerente apenas com o endpoint trivial existente.

Não há evidência de logs de senha/token, API keys de terceiros ou execução de SQL montado manualmente. Também não há superfície de upload, sessão ou token para avaliar.

Esses controles não compensam a ausência de autenticação/autorização se controllers de negócio forem adicionados.
