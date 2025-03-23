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

## Outbox Pattern
**Dispatcher**, belirli aralıklarla bu tabloyu tarar ve `status = NEW ya da FAILED` olan kayıtları **RabbitMQ**'ya gönderir.  
Retry limiti dolunca `status = DEAD` olur. DEAD olan mesajlar DLQ'ya hemen gonderilir. Dead mesajların outbox Table'dan silinmesi 3 yolla gerçekleşebilir: Timeout'a düşer (mesela 7 gün). Cleanup Service'i tarafından. Ya da compensating event'i (varsa/mümkümse) başarıyla işlenince 
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