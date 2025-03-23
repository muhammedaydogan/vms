package com.vm.vending.domain.model;

import com.vm.vending.domain.event.DomainEvent;
import com.vm.vending.domain.event.ProductPurchasedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VendingMachine {
    private UUID id;
    private Map<Product, Integer> stock = new HashMap<>();
    private final List<DomainEvent> domainEvents = new ArrayList<>();

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

        domainEvents.add(new ProductPurchasedEvent(
                this.id, user.getId(), product.getId(), LocalDateTime.now()
        ));
    }
    public Product findProductById(String productId) {
        return stock.keySet().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found in machine"));
    }

    public void increaseProductStock(Product product) {
        stock.put(product, stock.getOrDefault(product, 0) + 1);
    }
}
