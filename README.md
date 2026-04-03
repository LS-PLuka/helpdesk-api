# helpdesk-api — Backend Java

API REST para gerenciamento pessoal de chamados de suporte de TI,
desenvolvida com Java e Spring Boot, utilizando PostgreSQL como banco de dados.

---

## Sobre

Projeto pessoal criado para anotar e consultar chamados de suporte durante o
trabalho no dia a dia de infraestrutura de redes e TI. A ideia central é ter
um histórico de problemas resolvidos para consulta rápida quando um problema
parecido aparecer novamente.

Além da utilidade prática, o projeto serve como laboratório de aprendizado,
aplicando conceitos estudados em cursos da Alura:

- **GitFlow** — branches organizadas com main, develop e feature/*
- **CI/CD** — pipeline com GitHub Actions rodando testes a cada PR
- **Boas práticas REST** — códigos HTTP corretos, ResponseEntity, DTOs
- **SOLID** — separação de responsabilidades entre Controller, Service e Repository
- **Padrão Strategy e Chain of Responsibility** — validações de regras de negócio isoladas em classes independentes
- **Padrão DTO** — entidades nunca expostas diretamente na API
- **Testes unitários** — JUnit 5 + Mockito com padrão Triple A (Arrange, Act, Assert)
- **Dependabot** — atualização automática de dependências

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 4.x | Framework principal |
| Spring Data JPA | Persistência de dados |
| Spring Validation | Validação de entrada com Bean Validation |
| Spring Web | Endpoints REST |
| PostgreSQL | Banco de dados relacional |
| Lombok | Redução de boilerplate |
| Maven | Build e dependências |
| JUnit 5 + Mockito | Testes unitários |
| Docker | Container do banco de dados |
| GitHub Actions | Pipeline de CI |
| Dependabot | Atualização automática de dependências |

---

## Estrutura do projeto
```bash
src/main/java/com/helpdesk/
├── controller/         # Endpoints REST
├── service/            # Regras de negócio
│   └── validacao/      # Validações via Strategy + Chain of Responsibility
├── repository/         # Acesso ao banco de dados
├── model/
│   ├── entity/         # Entidades JPA
│   ├── dto/            # Request e Response
│   └── enums/          # Categoria e StatusChamado
└── exceptions/         # Tratamento global de exceções
```

---

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/chamados/list` | Lista todos os chamados |
| GET | `/api/chamados/get/{id}` | Busca chamado por ID |
| POST | `/api/chamados` | Abre novo chamado |
| DELETE | `/api/chamados/{id}` | Remove um chamado |

---

## Como rodar
```bash
# Clone o repositório
git clone https://github.com/LS-PLuka/helpdesk-api.git
cd helpdesk-api

# Suba o banco de dados com Docker
docker run --name chamados-db \
  -e POSTGRES_DB=chamados \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin123 \
  -p 5432:5432 \
  -d postgres:16

# Configure o application.properties em src/main/resources/
spring.datasource.url=jdbc:postgresql://localhost:5432/chamados
spring.datasource.username=admin
spring.datasource.password=admin123

# Build e execução
./mvnw spring-boot:run
```

> O Spring criará as tabelas automaticamente via `ddl-auto=update`.

---

## Fluxo de desenvolvimento
feature/* → develop  (CI roda os testes automaticamente)  
develop   → main     (CI roda os testes novamente)

Nenhum merge é permitido sem os testes passarem.
