package com.vm.vending.infrastructure.publisher;

public interface EventPublisher {
    void publish(String eventType, String payload) throws Exception;
}
