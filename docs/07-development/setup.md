# Setup e execução local

[← Índice](../README.md) · [Configuração](configuration.md) · [Testes](testing.md)

## Requisitos

- JDK 25.
- PostgreSQL acessível.
- Maven 3.9.x ou Maven Wrapper funcional.
- Schema previamente criado e compatível com as entidades, pois Hibernate usa `validate`.

## Preparação

1. Crie o database PostgreSQL (o nome configurado é `barbershop`).
2. Crie/aplique o schema. O repositório não fornece migration ou DDL, portanto esta é hoje uma etapa manual e não reproduzível somente a partir de comandos versionados.
3. Externalize as credenciais conforme [configuração](configuration.md).
4. Execute os testes e depois a aplicação.

Linux/macOS, se o wrapper estiver funcional:

```bash
./mvnw test
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Alternativa com Maven global:

```bash
mvn test
mvn spring-boot:run
```

Depois de iniciar, `GET http://localhost:8080/` deve responder `API rodando!`, salvo override de porta.

## Limitação reproduzida nesta análise

Em Windows, `.\mvnw.cmd test` falhou antes de iniciar o Maven com erro PowerShell `Não é possível indexar em uma matriz nula` / `Cannot start maven from wrapper`. Não havia `mvn` global disponível. O JDK local foi identificado corretamente.

Isso significa que os testes **não foram executados**, não que tenham falhado. A causa raiz do wrapper não foi alterada nem investigada além do escopo necessário desta documentação.
