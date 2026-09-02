# Exemplos de requisição

[← Endpoints](endpoints.md)

Somente um exemplo pode ser dado sem inventar contrato:

```bash
curl -i http://localhost:8080/
```

Resposta esperada no fluxo normal:

```http
HTTP/1.1 200
Content-Type: text/plain;charset=UTF-8

API rodando!
```

Não há exemplos JSON de criação/listagem porque não existem endpoints correspondentes. `ServicoRequest` não está ligado a uma rota.
