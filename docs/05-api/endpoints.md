# Referência da API HTTP

[← Índice](../README.md) · [Exemplos](request-examples.md)

## Endpoint existente

### `GET /`

Resposta simples indicando que o processo HTTP atende requisições.

**Parâmetros:** nenhum.

**Request body:** nenhum.

**Response body (texto):**

```text
API rodando!
```

**Status observável no fluxo normal:** `200 OK`.

**Autenticação/autorização:** nenhuma; `permitAll` se aplica.

Não há tratamento explícito de erro neste controller. Códigos 400/401/403/404/409 não fazem parte do comportamento implementado desse endpoint.

## Endpoints inexistentes

Não existem rotas HTTP para usuários, barbeiros, serviços, agendas, agendamentos, login ou notificações. Métodos públicos dos services não constituem API REST. Por isso, esta documentação não inventa URLs, JSONs ou status para eles.

Também não há OpenAPI/Swagger nem endpoint Actuator. O `/` não verifica conexão com PostgreSQL.
