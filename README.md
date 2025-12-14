# 🛡️ HabitPlus: API para Gerenciamento de Hábitos e Finanças Pessoais

> Uma API REST robusta desenvolvida com Spring Boot 3 e foco em segurança, autenticação JWT, controle de acesso baseado em roles e isolamento de dados por usuário.

## 🚀 Visão Geral do Projeto

**HabitPlus** é a API de backend para uma aplicação de gerenciamento de hábitos e controle financeiro pessoal. O principal objetivo deste projeto foi o **aprofundamento em boas práticas de segurança** no desenvolvimento de APIs REST, garantindo que apenas usuários autenticados e autorizados possam acessar e modificar seus próprios dados.

---

### 🔑 Conceitos de Segurança e Arquitetura Aplicados

O projeto foi arquitetado com foco em segurança e escalabilidade, implementando:

* **Autenticação JWT (Stateless):** Utilização de JSON Web Tokens para garantir a autenticidade das requisições sem manter estado no servidor. 
* **Controle de Acesso Baseado em Roles (`@PreAuthorize`):**
    * `USER`: Acesso restrito apenas aos seus próprios hábitos e transações financeiras (isolamento de dados).
    * `ADMIN`: Acesso total ao sistema, incluindo a listagem e gestão de todos os dados de usuários.
* **Isolamento de Dados:** Consultas e manipulações de recursos (hábitos, transações) são estritamente filtradas pelo ID do usuário autenticado, impedindo acesso indevido.
* **Segurança de Senhas:** Armazenamento seguro de senhas com o algoritmo **BCrypt**.

### 🌐 Estrutura e Padrões

* **RESTful Design:** Endpoints claros e semânticos.
* **HATEOAS:** Uso do Spring HATEOAS para incluir links de navegação (`_links`) nas respostas, permitindo a descoberta de recursos.
* **Tratamento de Exceções:** Implementação de um manipulador global para respostas de erro padronizadas.
* **Validação de Entrada:** Uso de anotações de validação em DTOs.

---

## 🛠️ Tecnologias Utilizadas

| Categoria | Tecnologia | Descrição |
| :--- | :--- | :--- |
| **Backend Principal** | **Java 21**, **Spring Boot 3** | Framework principal para construção da aplicação. |
| **Persistência** | Spring Data JPA (Hibernate), **MySQL** | Gerenciamento de dados relacional. |
| **Segurança** | **Spring Security** + **JWT** | Autenticação, autorização e filtros de segurança. |
| **Banco de Dados** | **Flyway** | Gerenciamento de versionamento e migrações do schema do banco. |
| **Produtividade** | Lombok, Maven | Redução de boilerplate code e gerenciamento de dependências. |
| **Outros** | Spring HATEOAS | Implementação de links de auto-descoberta. |

---

## 📂 Estrutura de Diretórios

A arquitetura segue o padrão de camadas (Controller, Service, Repository), além de módulos específicos para DTOs, segurança e configuração.
```
src/main/java/br/com/habitplus/ 
├── HabitPlusApplication.java 
├── config/ → Configurações globais (Security, JWT, Beans) 
├── controller/ → Endpoints REST (API) 
├── dto/ → Data Transfer Objects (Requisição/Resposta) 
├── entity/ → Modelos do Banco (Mapeamento JPA) 
├── exception/ → Tratamento de exceções e respostas de erro 
├── repository/ → Repositórios JPA (Acesso ao DB) 
└── service/ → Lógica de Negócio (Regras de acesso e manipulação)
```
## 🚀 Como Rodar Localmente

### Pré-requisitos
* **Java 21**
* **Maven**
* **MySQL 8+**

### Passos para Configuração

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU_USUARIO/habitplus.git](https://github.com/SEU_USUARIO/habitplus.git)
    cd habitplus
    ```

2.  **Configuração do Banco de Dados:**
    * Crie um banco de dados MySQL chamado `habitplus`.
    * Edite o arquivo `src/main/resources/application.properties` ou defina as seguintes **variáveis de ambiente**:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/habitplus
    spring.datasource.username=seu_usuario_db
    spring.datasource.password=sua_senha_db
    
    # Chave secreta forte para assinatura do JWT
    spring.jwt.secret=SUA_CHAVE_SECRETA_MUITO_FORTE_E_LONGA_AQUI
    ```

3.  **Executar a Aplicação:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4.  A API estará disponível em: `http://localhost:8080`

---

## ☁️ Deploy e Acesso Online

A aplicação está atualmente em **Produção** com deploy contínuo:
* **Hospedagem:** Render
* **Banco de Dados:** MySQL (Railway)

| Recurso | URL |
| :--- | :--- |
| **URL Base da API** | `[https://habit-plus-ql4i.onrender.com/api](https://habit-plus-ql4i.onrender.com)` |

---

## 📋 Guia de Endpoints (Exemplos)

Todos os endpoints protegidos exigem o cabeçalho **`Authorization: Bearer <seu_token_jwt>`**.

### Autenticação e Usuário (Públicos)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/register` | Cria um novo usuário. |
| `POST` | `/auth/login` | Realiza o login e **retorna o JWT**. |

### Recursos Protegidos

| Método | Endpoint | Descrição | Role Requerida |
| :--- | :--- | :--- | :--- |
| `GET` | `/habits` | Lista hábitos do usuário autenticado. | `USER` / `ADMIN` |
| `POST` | `/habits` | Cria um novo hábito para o usuário autenticado. | `USER` / `ADMIN` |
| `GET` | `/transactions` | Lista transações financeiras do usuário autenticado. | `USER` / `ADMIN` |
| `GET` | `/users` | **Lista todos os usuários do sistema** (Acesso total). | `ADMIN` |
| `GET` | `/users/{id}` | Busca um usuário específico (com isolamento). | `ADMIN` (Pode buscar qualquer um) |

---

## 🤝 Contribuições

Contribuições são muito bem-vindas! Se você tiver sugestões, encontrou um bug ou deseja implementar um novo recurso, sinta-se à vontade para:

1.  Abrir uma **Issue** para discutir a mudança.
2.  Criar um **Pull Request** com sua implementação.
