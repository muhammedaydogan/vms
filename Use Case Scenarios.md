# Use Case Scenarios

## 1. Register User
- POST `/api/user/register`
- [User Service] registers the user and returns credentials.

## 2. Login User
- POST `/api/user/login`
- [User Service] authenticates and returns a token (to be implemented).

## 3. View Products
- GET `/api/machines/{machineId}`
- [Vending Service] returns available products and their stock.

## 4. Purchase Product
- POST `/api/purchase`
- [Purchase Service] validates request and dispatches ProductPurchasedEvent.
- [Vending Service] listens and reduces stock.
- [User Service] optionally adjusts balance (currently NOT handled directly).
- [Vending Service] sends PurchaseConfirmedEvent.
- [Purchase Service] updates outbox message status accordingly.

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
