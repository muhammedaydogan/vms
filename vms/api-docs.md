# Vending Machine API Documentation

---
## User Operations

### 🔸 POST /api/user/register
New User

**Request Body:**
```json
{
  "username": "ahmet",
  "passwordHash": "1234"
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

### 🔸 POST /api/user/login
Login

**Request Body:**
```json
{
  "username": "ahmet",
  "passwordHash": "1234"
}
```
**Response Body:**
```
Login Successful
```
---

### 🔸 POST /api/user/logout
Logout

**Request Body:**
```json
{
  "username": "ahmet",
  "passwordHash": "1234"
}
```
**Response Body:**
```
Logout Successful
```

---

### 🔸 GET /api/user/{userId}/balance
Get Balance

**Response Body:**
```
350
```

---

### 💰 Add Balance
**POST** `/api/user/balance`

Adds balance to a user. Return current balance

**Request Body:**
```json
{
  "userId": "uuid",
  "balance": 100
}
```
**Response Body:**
```
450
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

## Product Operations

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
