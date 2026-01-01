# Microserviço de Usuários (ms-usuarios) - API REST

Este microserviço é uma API REST responsável pelo gerenciamento de usuários do sistema de Agendamento de Tarefas, incluindo cadastro, autenticação, autorização e validação de tokens JWT para integração com outros microserviços.

## Responsabilidades

- Expor endpoints REST para gerenciamento de usuários

- Cadastro, atualização e manutenção de usuários

- Autenticação de usuários (login)

- Geração e validação de JWT Token

- Controle de perfis e permissões

- Endpoint REST para validação de JWT por outros microserviços (ex: Agendador)

## 🔐 Segurança

- API protegida com Spring Security

- Autenticação baseada em JWT (JSON Web Token)

- Senhas armazenadas de forma segura (criptografia)

- Controle de acesso por perfis e permissões

## Tecnologias

- Java 17
- Spring Boot
- Spring Web (REST)
- Spring Data JPA
- Spring Security + JWT
- Maven
- Banco de Dados: PostgreSQL
- Postman (testes e validação dos endpoints)

## Arquitetura

### API REST organizada em camadas:

- Controller (REST Controllers)

- Service

- Business

- Infrastructure

### Segue boas práticas de Clean Code

### Preparada para arquitetura de microserviços

## Testes da API

### Os endpoints da API REST são testados utilizando o Postman, permitindo validar:

- Requisições HTTP (GET, POST, PUT, DELETE)

- Autenticação via JWT

- Fluxo de autorização

- Respostas e códigos HTTP

## Integração entre Microserviços

Esta API REST disponibiliza um endpoint para validação de JWT, permitindo que outros microserviços validem tokens de forma segura e desacoplada.

###  Autor

- Geisivan Vitena

### Contato

- Email: gsv1205@yahoo.com

- LinkedIn: https://www.linkedin.com/in/geisivan-vitena-a46168246/
