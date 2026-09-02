# Autenticação e autorização

[← API](endpoints.md) · [Segurança](../06-security/current-security.md)

## Comportamento atual

`SecurityConfig` desabilita CSRF, form login e HTTP Basic e aplica `anyRequest().permitAll()`. O `UserDetailsService` sempre lança `UsernameNotFoundException`. Portanto, não existe fluxo de login, sessão, JWT, refresh token nem identidade autenticada.

`Usuario.role` e `Usuario.ativo` não são usados pela cadeia de segurança. Não há `@PreAuthorize`, regras por path ou validação de ownership.

O BCrypt é usado somente quando `UsuarioService.criar` é chamado internamente. Isso protege o hash persistido, mas não torna autenticação existente.

Não foi possível determinar com segurança se a autenticação futura pretendida será sessão ou token.
