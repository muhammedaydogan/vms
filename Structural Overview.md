## Services & Domains

| Service                | Domain                      | Responsibility               |
|------------------------|-----------------------------|------------------------------|
| `user-service`         | `User`, `Auth`              | Register/Login, Balance      
| `vending-service`      | `VendingMachine`, `Product` | Purchasing, Stock Management 
| `payment-service`      | `Payment`                   | Card verification            
| `notification-service` | `EventLog`, `Message`       | DLQ, retry, email, vs        

## Events & Kafka Topics

| Event                      | Kafka Topic         |
|----------------------------|---------------------|
| `ProductPurchasedEvent`    | `product-purchased` 
| `BalanceInsufficientEvent` | `payment-faield`    

## Asynchronous Messaging Strategy

| Kullanım Amacı             | Tercih   | Sebep                            |
|----------------------------|----------|----------------------------------
| Outbox’tan publish         | RabbitMQ | Retry + DLQ + transactional yapı 
| Sistemler arası event bus  | Kafka    | Performans + replay + fan-out    
| Event sourcing / analytics | Kafka    | Data lake mantığı, replay        

