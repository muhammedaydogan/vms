# Use Case: Purchase Journey Through Frontend Interface

## 🟢 1. Register/Login

**Frontend Action:**

The user registers or logs in through the interface.

**Related service and endpoint:**

- user-service
    - POST /api/user/register
    - POST /api/user/login

---

## 🟡 2. View Products

**Frontend Action:**

The user selects a vending machine and views the products inside.

**Related service and endpoint:**

- vending-service
    - GET /api/machines/{machineId} → product + stock info

---

## 🔵 3. View Balance / Add Balance

**Frontend Action:**

The user views their current balance or adds balance (like a credit card simulation).

**Related service and endpoint:**

- user-service
    - GET /api/user/{userId}/balance
    - POST /api/user/add-balance

---

## 🔴 4. Purchase Product

**Frontend Action:**

The user selects a product and clicks the purchase button.

**Related service and request flow:**

- purchase-service
    - POST /api/purchase  
      ↓
        - Calls user-service: GET /api/user/{id}/balance
        - Calls vending-service: GET /api/machines/{id}  
        ↓ If balance is enough
          - Class vending-service: POST /api/machines/decrease-stock

If conditions are met:

- An event is created to deduct user balance (ProductPurchasedEvent)
- Saved as OutboxMessage in purchase-service DB
- Dispatcher sends the event to RabbitMQ
- user-service and vending-service consume the event via DLQ-enabled consumers and update data

---

## ⚙️ Flow Summary

| Step | Frontend Request              | Service           | Internal Communication                        |
|------|-------------------------------|-------------------|-----------------------------------------------|
| 1    | /api/user/register            | user-service      | -                                             |
| 2    | /api/user/login               | user-service      | -                                             |
| 3    | /api/user/{id}/balance        | user-service      | -                                             |
| 4    | /api/user/add-balance         | user-service      | -                                             |
| 5    | /api/machines/{id}           | vending-service   | -                                             |
| 6    | /api/purchase                 | purchase-service  | REST call to user-service & vending-service   |
