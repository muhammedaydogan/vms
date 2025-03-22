package com.vm.vending.domain.model;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VendingMachine {
    private UUID id;
    private Map<Product, Integer> stock = new HashMap<>();

    public boolean isProductAvailable(Product product) {
        return stock.getOrDefault(product, 0) > 0;
    }

    public void purchaseProduct(User user, Product product) {
        if (!isProductAvailable(product)) {
            throw new IllegalStateException("Product out of stock");
        }

        if (!user.hasEnoughBalance(new Money(product.getPrice()))) {
            throw new IllegalStateException("Insufficient balance");
        }

        user.decreaseBalance(new Money(product.getPrice()));
        stock.put(product, stock.get(product) - 1);

        // todo
        // domainEvents.add(new ProductPurchasedEvent(user.getId(), product.getId(), LocalDateTime.now()));
    }
}
