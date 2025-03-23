# Vending Machine System

## Technologies and Architectures

- **Backend:** Spring Boot (Java 21)
- **Database:** PostgreSQL, JPA
- **Event-Driven Architecture**
- **Asynchronous Messaging:** RabbitMQ & Kafka
- **API Documentation:** Swagger
- **Security & Error Management:** Outbox Pattern, Error event mechanism, Dead Letter Queue (DLQ)

## Architectural Components

### 1. Domain Modeling

- **Main Entities:** Product, VendingMachine, User
- **DDD Principles:**
    - **Aggregate Roots:** VendingMachine (manages stock & product selection), User (identity & balance)
    - **Repositories:** UserRepository, VendingMachineRepository
    - **Domain Events:** ProductPurchasedEvent, PurchaseConfirmedEvent, BalanceInsufficientEvent
    - **Value Objects:** Money

### 2. Event-Driven Architecture

- The system is designed based on the **Outbox Pattern** and **ACK Event** logic.
- Events generated in the domain layer are first written **transactionally to the Outbox table**.
- A dispatcher component periodically sends these events to the **RabbitMQ** queue.
- The consumer receiving the events produces an **acknowledgement (ACK) event** upon successful completion of the
  process (e.g., `PurchaseConfirmedEvent`).
- The ACK event is again published via the Outbox.
- **Kafka** is used for event propagation, and microservices listen to events from Kafka topics.
- **Error events** are processed for error management (using the DLQ mechanism).
- The system architecture is planned to be expandable to the **Saga Pattern** in the future.

### 3. API Design

- **RESTful API**
    - **Register / Login** (User authentication)
    - **Logout** (User authentication)
    - **Accept Payment**
    - **Select Product and/or Purchase**
    - **Refund Transaction**
    - **Stock Control**
- **Swagger UI** documentation.

### 4. Data Management and Persistence

- DB type: **PostgreSQL**
- **Each microservice is designed to be independent, having its own database.**
- With the **Outbox table**, messages are processed transactionally, ensuring system **availability and consistency**.

### 5. Security & Error Management

- **Error events** (e.g., PaymentFailedEvent, StockDepletedEvent, TransactionFailedEvent)
- **DLQ (Dead Letter Queue)** mechanism for processing failed messages

## Sample Product Data

| Product Name   | Price (Unit) |
|----------------|--------------|
| Su             | 25           
| Kola           | 35           
| Soda           | 45           
| Snickers       | 50           
| Cips           | 40           
| Çikolata       | 30           
| Enerji İçeceği | 60           
| Meyve Suyu     | 55           
| Protein Bar    | 45           
| Sakız          | 20           

## Optionals

- **Frontend Integration** with React
- **Observability / Monitoring:** Monitoring system metrics with Prometheus + Grafana, and for more advanced logging,
  using the ELK Stack (Elasticsearch, Logstash, Kibana).
- **Authentication & Security:** Basic Auth or JWT
- **Deployment:** Docker Compose, Kubernetes (K8s), Sonatype Nexus as docker image registry.
- **SAGA Pattern**
- **CQRS**
