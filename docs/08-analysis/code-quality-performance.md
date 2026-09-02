# Qualidade de código e performance

[← Dívida técnica](technical-debt.md)

## Qualidade

### Pontos positivos

- Packages e nomes refletem responsabilidades familiares a projetos Spring.
- Injeção por construtor e campos `final` facilitam testes.
- Services delimitam transações de leitura/escrita.
- Consultas de conflito são legíveis e usam parâmetros.
- Enums em string e `equals/hashCode` explícito por ID tornam intenção visível.
- Métodos de associação de agenda mantêm os dois lados.

### Limitações

- **Coesão:** em geral boa pelo tamanho, mas `AgendamentoService` acumula regras de calendário, estados, concorrência e resolução de entidades sem cobri-las integralmente.
- **Acoplamento:** services chamam services concretos; aceitável nesta escala, mas aumenta montagem de testes e pode criar ciclos com evolução.
- **SOLID:** separação em camadas existe, porém entidades dependem de JPA, Validation, Jackson e Lombok. Não é necessariamente inadequado para um monólito pequeno, mas não é domínio isolado.
- **Encapsulamento:** setters públicos permitem mudar status, relações e horários sem métodos de negócio.
- **Consistência:** timestamps usam tipos distintos; unicidade é tratada de modo diferente entre create/update; mensagens e visibilidade de `buscarPorId` variam.
- **Nomenclatura:** `SOMBRACELHA` parece erro ortográfico persistido; corrigi-lo exige migration/compatibilidade, não simples rename.
- **Código potencialmente morto:** DTOs de serviço, `conflitoCom`, `contemHorario`, `getDuracaoEmMinutos`, algumas queries e predicates não têm consumidores na aplicação atual. Podem ser preparação futura; não é seguro removê-los sem decisão de produto.

## Performance

- Todas as listas são não paginadas, incluindo notificações pendentes/falhas.
- Relações lazy evitam eager global, mas DTO mapping/serialização futura pode gerar N+1.
- A busca de conflito carrega uma `List` inteira quando apenas existência seria necessária.
- `verificarConflito` filtra em memória o ID ignorado; a exclusão poderia ocorrer na consulta.
- Índices separados existem, mas a consulta central combina barbeiro, status e tempo; medir plano é necessário.
- Não há cache, batch ou processamento assíncrono; no tamanho atual, não há evidência de que sejam necessários.

Não foi possível medir latência, throughput, planos SQL ou volume sem schema/dados/execução. Os pontos acima são riscos estáticos, não gargalos comprovados.
