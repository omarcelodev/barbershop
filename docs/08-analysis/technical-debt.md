# Dívida técnica

[← Índice](../README.md) · [Roadmap](improvement-roadmap.md)

## Itens priorizados

### 1. Criação de agendamento sem associação ao barbeiro

- **Onde:** `AgendamentoService.criar`.
- **Problema:** o barbeiro é resolvido/validado, mas nunca passado a `agendamento.setBarbeiro(...)`.
- **Impacto:** persistência deve violar `barbeiro_id nullable=false`; o caso de uso central não conclui normalmente.
- **Prioridade:** Crítica.
- **Sugestão:** associar explicitamente e criar teste de integração. Não aplicado.

### 2. Concorrência de reservas não garantida

- **Onde:** `verificarConflito`, `findConflitantes` e `@Version`.
- **Problema:** duas transações podem ler “sem conflito” e inserir linhas diferentes; lock otimista por entidade não detecta isso.
- **Impacto:** dupla reserva e corrupção da agenda lógica.
- **Prioridade:** Crítica.
- **Sugestão:** desenho transacional/constraint/lock por recurso, validado com teste concorrente.

### 3. Agenda semanal desconectada

- **Onde:** `AgendaService` versus `AgendamentoService`.
- **Problema:** agendamento não consulta dia/janela disponível.
- **Impacto:** horários fora do expediente são aceitos internamente (se o save for corrigido).
- **Prioridade:** Alta.
- **Sugestão:** regra única que valide começo e fim contra agenda e exceções.

### 4. Máquina de estados inconsistente

- **Onde:** `Status`, `Agendamento.isAtivo`, `reagendar`, `concluir`, consulta de conflito.
- **Problema:** `REAGENDADO` vira estado do mesmo registro, deixa de bloquear e não pode mudar novamente; `CONFIRMADO` nunca é produzido; cancelado pode ser concluído.
- **Impacto:** ocupação incorreta e histórico ambíguo.
- **Prioridade:** Alta.
- **Sugestão:** desenhar transições autorizadas; decidir se reagendamento atualiza o registro ou substitui por outro com histórico.

### 5. Schema não reproduzível e índice incoerente

- **Onde:** `ddl-auto=validate`, ausência de migrations, `Notificacao.@Table`.
- **Problema:** não há DDL; índice `idx_notificacao_usuario` aponta para coluna não mapeada.
- **Impacto:** onboarding bloqueado e provável falha de validação/criação de metadata.
- **Prioridade:** Alta.
- **Sugestão:** inspecionar schema real, corrigir modelo em etapa própria e versionar baseline.

### 6. Credencial e configuração acopladas ao ambiente

- **Onde:** `application.properties`.
- **Problema:** senha literal, URL local, SQL ligado e sem profiles.
- **Impacto:** exposição, pouca portabilidade e configuração insegura de produção.
- **Prioridade:** Alta.
- **Sugestão:** variáveis/secrets, profiles e rotação.

### 7. Fronteira HTTP, DTOs e erros incompletos

- **Onde:** `controller`, `dto`, ausência de advice.
- **Problema:** apenas endpoint teste; DTOs só para serviço; exceptions sem contrato.
- **Impacto:** API não utilizável e evolução tende a acoplar entidades à web.
- **Prioridade:** Alta.
- **Sugestão:** contratos por caso de uso e erro global antes de ampliar rotas.

### 8. Validações contornáveis ou inconsistentes

- **Onde:** setters Lombok, callbacks, updates de serviço/agenda, services.
- **Problema:** invariantes só no lifecycle ou criação; setters permitem estados intermediários; normalização do telefone pode ocorrer tarde.
- **Impacto:** comportamento depende do caminho de escrita e do banco.
- **Prioridade:** Média.
- **Sugestão:** invariantes consistentes na fronteira/domínio e constraints físicas.

### 9. Listagens sem paginação e possíveis N+1

- **Onde:** todos os repositories/services de lista; relações lazy.
- **Problema:** coleções completas e acesso futuro a relações podem multiplicar queries.
- **Impacto:** memória/latência conforme volume.
- **Prioridade:** Média (preventiva; sem evidência de carga atual).
- **Sugestão:** paginação, projections/fetch planejado e métricas.

### 10. Testabilidade e build local

- **Onde:** único `@SpringBootTest`, datasource real e wrapper Windows.
- **Problema:** nenhuma cobertura de regra; test depende de ambiente; wrapper falhou na análise.
- **Impacto:** regressões e onboarding lento.
- **Prioridade:** Alta.
- **Sugestão:** reparar build em tarefa própria e implantar pirâmide de testes reproduzível.

Não foram encontrados TODO/FIXME, God Classes ou duplicação extensa. O problema dominante não é tamanho, mas invariantes incompletas e ausência de fronteiras operacionais.
