# 📘 Fórum API – Spring Boot + JWT

API REST desenvolvida em Java com Spring Boot que simula um fórum de discussão, permitindo cadastro de usuários e CRUD completo de tópicos, com segurança baseada em JWT e controle de autorização para garantir que apenas o autor do tópico possa atualizá-lo ou removê-lo.

# 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- PostgreSQL
- Maven
- Postman (testes)
- Hibernate

# 🧱 Arquitetura do Projeto

O projeto segue uma arquitetura em camadas, separando responsabilidades:

```
src/main/java/com/forum/hub
│
├── controller   → Camada de entrada (REST)
├── service      → Regras de negócio
├── repository   → Acesso ao banco de dados
├── entity       → Entidades JPA
├── dto          → Objetos de transferência de dados
└── security     → Configuração de segurança e JWT
```

# 🔐 Segurança e Autenticação

- Autenticação feita via JWT
- Usuário faz login e recebe um token
- O token deve ser enviado no header:

```
Authorization: Bearer {TOKEN}
```

# 🛡️ Regras de autorização

| Ação             | Regra               |
| ---------------- | ------------------- |
| Criar tópico     | Usuário autenticado |
| Listar tópicos   | Público             |
| Buscar tópico    | Público             |
| Atualizar tópico | Apenas o autor      |
| Deletar tópico   | Apenas o autor      |

# 📦 Modelos Principais

# 👤 Usuário

- id
- nome
- email
- senha (criptografada)

# 📝 Tópico

- id
- título
- mensagem
- autor (Usuário)
- data de criação

# 📑 Endpoints da API

🔐 Autenticação

➕ Cadastro de usuário

```
POST /auth/register

Body

{
  "nome": "Thayza",
  "email": "thayza@email.com",
  "senha": "123456"
}
```
🔑 Login

```
POST /auth/login

Body

{
  "email": "thayza@email.com",
  "senha": "123456"
}

Resposta

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

#📝 Tópicos

➕ Criar tópico (JWT)

```
POST /topicos
{
  "titulo": "Dúvida sobre Spring Boot",
  "mensagem": "Como funciona o Spring Security com JWT?"
}
```
```
- Listar tópicos (público)
GET /topicos

- Buscar tópico por ID
GET /topicos/{id}

- Atualizar tópico (somente autor)
PUT /topicos/{id}

- Deletar tópico (somente autor)
DELETE /topicos/{id}
```

# 🧪 Testes com Postman

1. Criar usuário
2. Fazer login e copiar o token
3. Configurar no Postman:
- Authorization → Bearer Token
4. Testar criação, edição e remoção de tópicos
5. Testar acesso com outro usuário (403 Forbidden)

# ⚙️ Configuração do Banco de Dados

Arquivo application.properties:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/forumdb
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

```

# ▶️ Como Executar o Projeto

 ```
# Clonar o repositório
git clone https://github.com/seu-usuario/forum-Hub.git

# Entrar no projeto
cd forum-api

# Executar
./mvnw spring-boot:run
```
API disponível em:

```
http://localhost:8080
```
# 👩‍💻 Autora

Thayza Sila
Estudante de Análise e Desenvolvimento de Sistemas
Projeto desenvolvido com foco em aprendizado de Spring Boot, Segurança e APIs REST
