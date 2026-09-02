# Tratamento de erros

[← Camadas](layers.md) · [API](../05-api/endpoints.md)

## Estado atual

Não há `@ControllerAdvice`, `@ExceptionHandler`, modelo de erro ou mapeamento explícito para status HTTP. O único endpoint não lança erros de domínio em seu fluxo normal.

Os services usam:

- `EntityNotFoundException` para IDs/e-mail ausentes;
- `IllegalArgumentException` para duplicidade e entrada incompatível;
- `IllegalStateException` para indisponibilidade, conflito e transições;
- tentativa de traduzir `OptimisticLockException` em `IllegalStateException` no agendamento.

Erros de banco como constraint única/FK, Bean Validation, optimistic locking traduzido pelo Spring ou violação de coluna não têm política local definida. A exceção efetivamente observada pode variar conforme flush/tradução do Spring, portanto não se deve prometer status HTTP para esses casos.

## Recomendações

Definir um envelope com código estável, mensagem segura, timestamp, path e detalhes de campos; mapear 400/404/409 de forma consistente; ocultar stack traces; registrar correlation ID; e testar todos os mappings. Mensagens internas atuais incluem IDs/e-mails e devem ser revisadas antes de serem devolvidas publicamente.
