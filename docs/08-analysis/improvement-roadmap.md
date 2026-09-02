# Roadmap de melhorias

[← Índice](../README.md) · [Dívida técnica](technical-debt.md)

Nenhum item foi implementado nesta etapa.

## Prioridade 1 — Crítico

1. **Restaurar a integridade do agendamento:** associar barbeiro, validar agenda e escrever testes do fluxo. É o núcleo funcional e hoje não persiste corretamente.
2. **Garantir concorrência no banco/transação:** duas reservas simultâneas não podem vencer. `@Version` atual não atende inserts concorrentes.
3. **Externalizar/rotacionar credencial e negar acesso por padrão:** preparar uma base segura antes de expor qualquer rota de negócio.
4. **Versionar o schema e resolver o índice inválido:** sem baseline reproduzível, desenvolvimento e deploy não são confiáveis.

## Prioridade 2 — Importante

1. Formalizar estados e transições de agendamento, inclusive significado de `REAGENDADO`/`CONFIRMADO`.
2. Reparar o Maven Wrapper e criar profile de teste com PostgreSQL isolado/migrations.
3. Cobrir services, repositories, concorrência e segurança com testes.
4. Projetar API REST com DTOs, validação, erros padronizados e OpenAPI.
5. Definir autenticação, roles e ownership antes de conectar services aos controllers.
6. Uniformizar invariantes entre create/update e no banco.

## Prioridade 3 — Evolução

1. Paginar listagens e otimizar fetch/query de existência com medições.
2. Implementar entrega de notificações com adapter por canal, retry idempotente e observabilidade.
3. Criar profiles, containerização, CI, SCA e cobertura.
4. Adicionar health/readiness reais, logs estruturados, métricas e correlation IDs.
5. Registrar snapshot de preço/duração se o histórico comercial exigir imutabilidade.

## Prioridade 4 — Futuro

1. Modelar exceções de agenda, feriados, pausas e múltiplos turnos conforme requisitos reais.
2. Avaliar eventos/outbox e processamento assíncrono apenas quando notificações/escala justificarem.
3. Avaliar cache, separação modular ou serviços independentes somente com evidência de carga/equipe.
4. Evoluir auditoria e relatórios sem enfraquecer ownership e privacidade.

Ordem sugerida: integridade → segurança/schema → testes → API → operação → escala. Isso reduz o risco de construir uma interface sobre regras instáveis.
