package com.vm.vending.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VendingMachine {
    private String id;
    private Map<Product, Integer> stock = new HashMap<>();
    private Money currentBalance = new Money(0);

    public void insertMoney(Money money) {
        this.currentBalance = new Money(currentBalance.getAmount() + money.getAmount());
    }

    public boolean canDispense(Product product) {
        return stock.getOrDefault(product, 0) > 0 && currentBalance.getAmount() >= product.getPrice();
    }
}
