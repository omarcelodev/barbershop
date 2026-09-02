# Riscos e melhorias de segurança

[← Segurança existente](current-security.md) · [Roadmap](../08-analysis/improvement-roadmap.md)

## Riscos encontrados

| Severidade | Risco | Evidência e impacto |
|---|---|---|
| **Alto** | Credencial de banco versionada | `application.properties` contém usuário `postgres` e senha literal. Mesmo parecendo local, reutilização ou exposição do ambiente torna acesso indevido possível. |
| **Alto** | Configuração global `permitAll` | Qualquer controller de negócio futuro ficará público por padrão; não existem checks de role ou ownership. Hoje o impacto HTTP é limitado ao `/`. |
| **Alto** | Integridade concorrente de agenda | `consultar conflitos -> inserir` e `@Version` por linha permitem, em tese, dois inserts sobrepostos. É risco de integridade, não autenticação. |
| **Médio** | CSRF desabilitado sem arquitetura autenticada definida | Seguro para API stateless bem configurada, mas perigoso se futuramente forem usados cookies/sessão. |
| **Médio** | Mass assignment potencial | Entidades têm setters públicos e faltam DTOs para quase todos os recursos. O risco se materializaria se fossem recebidas diretamente por controllers. |
| **Médio** | Ausência de validação de ownership | Services aceitam IDs arbitrários e não recebem principal autenticado. Ao expor rotas, pode resultar em IDOR/broken access control. |
| **Médio** | Falta de política de erros | Exceções não são sanitizadas; configurações padrão podem revelar detalhes conforme ambiente. |
| **Médio** | Sem rate limit/brute-force protection | Não existe login hoje; será necessário antes de autenticação pública. |
| **Baixo** | SQL detalhado habilitado | `show-sql=true` pode expor estrutura/valores e gerar ruído/custo em produção. |
| **Indeterminado** | CVEs de dependências | Auditoria não foi executada devido ao wrapper local quebrado; não há evidência suficiente para classificar vulnerabilidade. |

Não foi encontrado JWT, segredo JWT, CORS customizado ou autorização parcial. Ausência de CORS customizado não é, por si só, vulnerabilidade.

## Melhorias recomendadas

1. Remover credenciais do arquivo versionado, rotacionar a senha se tiver sido usada fora de ambiente descartável e usar variáveis/secret manager.
2. Trocar para deny-by-default antes de adicionar endpoints; liberar explicitamente apenas health/login/cadastro conforme requisitos.
3. Definir autenticação e uma matriz `operação × role × ownership`, testada com Spring Security Test.
4. Usar DTOs allow-list e `@Valid`; jamais aceitar `role`, `ativo`, `senhaHash` ou ownership por binding genérico.
5. Garantir exclusão concorrente no banco/transação para reservas e testar com duas threads.
6. Padronizar respostas de erro e desabilitar detalhes/SQL em produção.
7. Adicionar rate limiting, auditoria de ações sensíveis, rotação de segredo e SCA na pipeline.

Nenhuma dessas melhorias foi implementada nesta etapa.
