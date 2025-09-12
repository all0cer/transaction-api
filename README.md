# API de Transações Bancárias

## Visão Geral
Esta aplicação é uma API RESTful para gerenciamento de transações financeiras, com autenticação JWT, controle de transações em memória e geração de estatísticas de transações dos últimos 60 segundos. O banco de dados utilizado é PostgreSQL rodando em Docker.

A API possui endpoints para:
- Registro de usuários
- Login e geração de token JWT
- Criação de transações
- Consulta de estatísticas das transações

A segurança da API inclui:
- JWT para autenticação
- CSRF desabilitado (API REST stateless)
- Content Security Policy (CSP)
- HSTS
- Rate limiting por IP para login
- Validações de entrada via `@Valid` e Bean Validation

---

## Tecnologias
- Java 17
- Spring Boot 3.5.5
- Spring Security
- Spring Data JPA
- Flyway
- PostgreSQL
- Bucket4j (Rate Limiting)
- JWT (com Auth0 Java JWT)
- Springdoc OpenAPI (Swagger)

---

## Configuração do Banco de Dados (Docker)
O banco de dados PostgreSQL deve estar rodando via Docker, configure o properties de acordo com seu banco de dados.


```spring.jackson.deserialization.fail-on-unknown-properties=true

spring.datasource.url= URL_DO_SE_BANCO
spring.datasource.username= USUARIO
spring.datasource.password= SENHA
spring.datasource.driver-class-name=org.postgresql.Driver


spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration


spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect


api.security.token.secret=${JWT_SECRET:my-secret-test}
```


# Endpoints da API

## Autenticação e Registro

### POST `/auth/register`

Registra um novo usuário.

**Request Body:**

```
{
"login": "usuario1",
"password": "senha123",
"role": "USER"
}
```


# Endpoints da API

## Autenticação e Registro

### POST `/auth/register`

Registra um novo usuário.

**Request Body:**

```
{
"login": "usuario1",
"password": "senha123",
"role": "USER"
}
```

**Response:**

- `200 OK` → Usuário registrado com sucesso
- `400 Bad Request` → Login já existe
- `422 Unprocessable Entity` → Campos inválidos
- `500 Internal Server Error` → Falha ao salvar usuário




### POST `/auth/login`

Realiza login e retorna token JWT

**Request Body:**

```
{
  "login": "usuario1",
  "password": "senha123"
}

```

**Response:**
```
{
  "token": "token.jwt.aqui"
}
```

- `401 Unauthorized` → Credenciais inválidas
- `429 Too Many Requests` → Limite de tentativas de login atingido


## Transações

### POST `/transacao`

Registra uma transação (requer autenticação JWT).

**Request Body:**

```
{
  "value": 150.0,
  "date": "2025-09-12T14:00:00Z"
}

```

**Response:**

- `201 Created` → Transação criada com sucesso
- `422 Unprocessable Entity` → Dados inválidos
- `401 Unauthorized` → Token JWT ausente ou inválido

### GET `/transacao`

Retorna estatísticas das transações dos últimos 60 segundos.

**Response Body:**

```
{
  "count": 5,
  "sum": 500.0,
  "average": 100.0,
  "min": 50.0,
  "max": 200.0
}
```

- `201 Created` → Transação criada com sucesso
- `422 Unprocessable Entity` → Dados inválidos
- `401 Unauthorized` → Token JWT ausente ou inválido


Segurança
---
- Autenticação baseada em JWT
- Endpoints /auth/register e /auth/login são públicos
- Endpoints de transação requerem autenticação
- CSP e HSTS aplicados
- Rate limiting aplicado no login
---

**Swagger (OpenAPI):**

Com Springdoc, os endpoints podem ser visualizados em:

- `http://localhost:8080/swagger-ui/index.html`
- `http://localhost:8080/v3/api-docs`  
