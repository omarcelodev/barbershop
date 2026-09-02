# DTOs e mapeamento

[← Camadas](layers.md)

## Estado atual

Somente o recurso serviço possui DTOs:

| DTO | Campos | Finalidade observável |
|---|---|---|
| `ServicoRequest` | `nome`, `preco`, `duracao` | Entrada validável para criar/alterar serviço |
| `ServicoResponse` | `id`, `nome`, `preco`, `duracao`, `rapido` | Saída sem detalhes JPA; `rapido` deriva de `isRapido()` |

`ServicoResponse.from(Servico)` é o único mapper, implementado como factory estática. Não existe conversão explícita de `ServicoRequest` para entidade e nenhum controller usa ambos. Para as demais entidades não há DTOs.

## Consequências

A intenção de separar API e persistência aparece apenas parcialmente. Se entidades forem expostas diretamente no futuro, poderão surgir contratos acoplados ao schema, problemas com proxies lazy e mass assignment por setters públicos. `@JsonIgnore` protege `senhaHash` numa serialização padrão, mas não substitui DTO explícito.

## Recomendações

- Criar DTOs por caso de uso, não necessariamente um espelho por entidade.
- Manter role, ativo, IDs de ownership e timestamps fora de requests públicos quando não forem controláveis pelo cliente.
- Centralizar mapeamento simples em factories/classes pequenas; ferramenta automática só se o volume justificar.
- Aplicar `@Valid` nos futuros controllers; hoje as constraints dos records não são acionadas por HTTP.
