## Services & Domains
| Service                | Domain                      | Responsibility |
|------------------------|-----------------------------|--|
| `user-service`         | `User`, `Auth`              | Register/Login, Balance
| `vending-service`      | `VendingMachine`, `Product` | Purchasing, Stock Management
| `payment-service`      | `Payment`                   | Card verification
| `notification-service` | `EventLog`, `Message`       | DLQ, retry, email, vs

## Events & Kafka Topics
| Event | Kafka Topic |
|--|--|
|`ProductPurchasedEvent`| `product-purchased`
|`BalanceInsufficientEvent` | `payment-faield`
