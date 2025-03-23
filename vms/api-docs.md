# Vending Machine API Dokümantasyonu

---
## User Operations

### 🔸 POST /api/auth/register
New User

**Request Body:**
```json
{
  "username": "ahmet",
  "password": "1234"
}
```

**Response:**
```json
{
  "id": "uuid",
  "username": "ahmet"
}
```

---

### 🔸 POST /api/auth/login 
Login

**Request Body:**
```json
{
  "username": "ahmet",
  "password": "1234"
}
```

---

## Purchasing

### 🔸 POST /api/purchase

Purchase from a vending machine by user identity

**Request Body:**
```json
{
  "userId": "uuid",
  "vendingMachineId": "uuid",
  "productId": "1"
}
```

**Response:**
```json
{
  "message": "Product purchased successfully"
}
```

---

## Ürün İşlemleri

### 🔸 GET /api/machines/{machineId}/products
List stocks on a vending machine

**Response:**
```json
[
  {
    "id": "1",
    "name": "Su",
    "price": 25,
    "quantity": 10
  }
]
```

---

## ⚙ Event & Outbox

- All domain events will be written into `outbox` table by Outbox Pattern
- Then will be published over RabbitMQ.
- Then other services will gather them over Kafka.
- If error occurs before it has beensent the related domain event goes to `event.dlq` queue.

---

To be continued...
