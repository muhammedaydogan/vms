# Structural Overview

## Microservice Modules
```
vms/
├── vms-parent/
├── vms-common/
│   └── shared domain, events, outbox entity, API DTOs
├── user-service/
├── vending-service/
├── purchase-service/
└── notificaiton-service/ (future)

```

## Services & Domains

| Service                                          | Domain                      | Responsibility               |
|--------------------------------------------------|-----------------------------|------------------------------|
| `user-service`                                   | `User`, `Auth`              | Register/Login, Balance      
| `vending-service`                                | `VendingMachine`, `Product` | Purchasing, Stock Management 
| `payment-service`                                | `Payment`                   | Card verification            
| `notification-service` (Not available currently) | `EventLog`, `Message`       | DLQ, retry, email, vs        

## Events & Kafka Topics

| Event                      | Kafka Topic         |
|----------------------------|---------------------|
| `ProductPurchasedEvent`    | `product-purchased` 
| `BalanceInsufficientEvent` | `payment-faield`
| `ProductPurchasedEvent`    | `payment-faield`
| `PurchaseRollbackEvent`    | `payment-faield`
can be extended

## Asynchronous Messaging Strategy

| Kullanım Amacı             | Tercih   | Sebep                            |
|----------------------------|----------|----------------------------------
| Outbox’tan publish         | RabbitMQ | Retry + DLQ + transactional yapı 
| Sistemler arası event bus  | Kafka    | Performans + replay + fan-out    
| Event sourcing / analytics | Kafka    | Data lake mantığı, replay        


## Event Recovery
- Failed messages are retried.
- After exceeding max retry, messages are moved to DLQ.
- DLQ Consumer may emit a rollback/compensating event.

## Dead Message Handling
Messages in DLQ can be sent to related places by kafka for like `Auditing`, `Analytics` and `Replay` etc.

Deletion of dead messages from outbox table can be done in 3 ways:
- It falls into timeout (for example 7 days).
- By Cleanup Service.
- Or when rollback/compensation event (if available/possible) is successfully processed
### 🔄 Event Akışı

```plaintext
Domain Layer
   ⬇
Outbox Table (status = NEW)
   ⬇
Dispatcher → RabbitMQ / Kafka
   ⬇
Consumer (Event işlenir)
   ⬇
ACK Event (örneğin PurchaseConfirmedEvent)
   ⬇
Yeni Outbox kaydı
   ⬇
Dispatcher → RabbitMQ / Kafka
```
