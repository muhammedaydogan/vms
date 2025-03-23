package com.vm.vending.infrastructure.messaging.publisher;

public interface EventPublisher {
    void publish(String eventType, String payload) throws Exception;
}
