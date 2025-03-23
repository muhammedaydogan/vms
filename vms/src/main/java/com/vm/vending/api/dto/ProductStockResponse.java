package com.vm.vending.api.dto;

/**
 * Represents products that exists <br> (meaning that stock amount > 0)
 */
public record ProductStockResponse(String id, String name, int price, int quantity) {
}
