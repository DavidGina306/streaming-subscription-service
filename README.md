# Streaming Subscription Service 🚀

Este projeto é um microserviço robusto para gestão de usuários e assinaturas de streaming, desenvolvido com **Java 17**, **Spring Boot 3** e fundamentado nos princípios da **Arquitetura Hexagonal (Ports & Adapters)**.

O sistema gerencia o ciclo de vida completo do cliente, desde o cadastro inicial até a renovação automática de planos com tratamento de falhas e integração com mensageria.

---

## 🏗️ Arquitetura e Design

A aplicação foi estruturada para garantir o desacoplamento total entre as regras de negócio e as tecnologias de infraestrutura:

* **Domain & Use Cases (Core):** Contém as entidades de domínio puras e a orquestração dos fluxos de negócio (Interactors).
* **Ports:** Interfaces que definem os contratos de entrada (Inbound) e saída (Outbound) do Core.
* **Adapters (Infrastructure):** Implementações tecnológicas específicas para MongoDB, Kafka e controllers REST.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17 & Spring Boot 3**
* **MongoDB:** Banco de dados NoSQL para persistência de Usuários e Assinaturas.
* **Kafka:** Mensageria para disparar eventos de renovação e status.
* **Jakarta Validation:** Validação rigorosa de payloads nas requisições.
* **Docker & Docker Compose:** Containerização completa da stack (App, DB, Kafka).
* **Swagger (OpenAPI 3):** Documentação técnica e interface de testes interativa.

---

## 📋 Requisitos de Negócio Implementados

### 1. User Management
* Cadastro de usuários com persistência em MongoDB.
* Validação de campos obrigatórios e formato de e-mail.

### 2. Subscription Management
* **Regra de Unicidade:** Um usuário pode possuir apenas uma assinatura com status ACTIVE por vez.
* **Planos Suportados:**
  * BASICO: R$ 19,90/mês
  * PREMIUM: R$ 39,90/mês
  * FAMILIA: R$ 59,90/mês
* **Datas Customizáveis:** Suporte para definição de startDate e expirationDate via payload.

### 3. Automatic Renewal (Scheduler)
* Agendador diário que identifica assinaturas com vencimento no dia atual.
* **Política de Retentativas:** Até 3 tentativas em caso de falha no processamento.
* **Suspensão Automática:** Transição para o status SUSPENDED após a terceira falha consecutiva.

### 4. Cancellation
* Endpoint para cancelamento imediato, alterando o status para CANCELED.

---

## 🚀 Como Executar

### Pré-requisitos
* Docker e Docker Compose instalados.

### Passo a Passo
1. Clone o repositório.
2. Na raiz do projeto, suba os containers:
   docker compose up --build -d
3. Acesse a documentação Swagger em: 
   http://localhost:8087/api/swagger-ui/index.html

---

## 📡 API Endpoints

### Users
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | /v1/users | Registra um novo usuário e retorna seu UUID. |

### Subscriptions
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | /v1/subscriptions | Cria uma assinatura vinculada a um userId. |
| PATCH | /v1/subscriptions/{id}/cancel | Realiza o cancelamento de uma assinatura. |

---

## 🧪 Exemplo de Uso (Payloads)

### 1. Criar Usuário (POST /v1/users)

{
  "name": "David Oliveira",
  "email": "david.oliveira@example.com"
}

### 2. Criar Assinatura (POST /v1/subscriptions)

{
  "userId": "uuid-gerado-no-passo-1",
  "plan": "PREMIUM",
  "startDate": "2025-03-10",
  "expirationDate": "2025-04-10"
}

---

## 👨‍💻 Autor
**David Oliveira** - Software Developer