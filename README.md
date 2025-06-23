# Golden Raspberry Awards API

API REST para gerenciar os prêmios do Golden Raspberry Awards (Razzies).

## Pré-requisitos

- Java 21
- Maven 3.9+
- Git

## Tecnologias Utilizadas

- Spring Boot 3.5.0
- H2 Database
- Flyway para migração de banco de dados
- Lombok para redução de boilerplate
- JUnit e AssertJ para testes

## Configuração e Execução

### Clonando o Repositório


### Compilando o Projeto
mvn clean install

### Executando os Testes
mvn test


### Executando a Aplicação
mvn spring-boot:run

A aplicação estará disponível em: `http://localhost:8080`

## Endpoints da API

### Obter Intervalos de Prêmios
GET /awards/intervals


## Base de Dados

O projeto utiliza um banco de dados H2 em memória. O console do H2 pode ser acessado em:

[http://localhost:8080/h2-console

Credenciais de acesso:
- JDBC URL: `jdbc:h2:mem:awards_db`
- Username: `sa`
- Password: ` ` (vazio)

## Dados

Os dados são carregados automaticamente a partir do arquivo CSV localizado em:
src/main/resources/static/Movielist.csv



