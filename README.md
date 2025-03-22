# Vending Machine System

## Teknoloji ve Mimariler
- **Backend:** Spring Boot (Java 21)
- **Database:** PostgreSQL
- **Event-Driven Architecture:** Apache Kafka & RabbitMQ
- **Asenkron Mesajlaşma:** Outbox Pattern (RabbitMQ & Kafka)
- **API Dokümantasyonu:** Swagger
- **Güvenlik & Hata Yönetimi:** Error event mekanizması, Dead Letter Queue (DLQ)

## Mimari Bileşenler
### 1. **Domain Modelleme**
- **Ana Entity'ler:** Product, VendingMachine, User
- **DDD Prensipleri:**
  - **Aggregate Roots:** VendingMachine (Ürün stoğu ve seçimi burada yönetilir), User (kimlik ve bakiye yönetimi)
  - **Repositories:** Veritabanı erişimi için
  - **Domain Events:** Transaction başarı/hata eventleri, stok yönetimi eventleri, kullanıcı işlemleri

### 2. **Event-Driven Mimari**
- **Outbox Pattern** iki aşamalı çalışır:
  - **Birinci aşama:** Event, ilgili işlemle aynı transaction içinde PostgreSQL üzerindeki "outbox" tablosuna yazılır.
  - **İkinci aşama:** Bir dispatcher bu tablodaki eventleri okuyarak RabbitMQ’ya publish eder. RabbitMQ, bu eventleri güvenli biçimde Kafka’ya iletir.
- **Kafka**, event propagation için kullanılır ve mikroservisler Kafka topic'lerinden eventleri dinler.
- **Error eventleri**, hata yönetimi için işlenir (DLQ mekanizması ile).

### 3. **API Design**
- **RESTful API**
  - **Register / Login** (User authentication)
  - **Logout** (User authentication)
  - **Accept Payment**
  - **Select Product and/or Purchase**
  - **Refund Transaction**
  - **Stock Control**
- **Swagger UI** dokümantasyonu.

### 4. **Veri Yönetimi ve Kalıcılık**
- DB type: **PostgreSQL**
- **Her mikroservis kendi veritabanına sahip olacak şekilde bağımsız tasarlanmıştır.**
- **Outbox tablosu** ile mesajların transactional olarak işlenmesi ve sistem **erişilebilirliği ve tutarlılığı**nın sağlanması.

### 5. **Güvenlik & Hata Yönetimi**
- **Hata eventleri** (örneğin, PaymentFailedEvent, StockDepletedEvent, TransactionFailedEvent)
- **DLQ (Dead Letter Queue)** mekanizması ile başarısız mesajların işlenmesi

## Örnek Ürün Verileri
| Ürün Adı | Fiyat (Birim) |
|----------------|---|
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
- **Observability / Monitoring:** Prometheus + Grafana ile sistemsel metrikleri izleme, biraz abartalım dersek ELK Stack (Elasticsearch, Logstash, Kibana) ile log yönetimi.
- **Authentication & Security:** Basic Auth or JWT
- **Deployment:** Docker Compose, Kubernetes (K8s), Sonetype Nexus as docker image registry.
