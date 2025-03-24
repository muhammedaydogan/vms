package com.vm.common.infrastructure.messaging.publisher;

public interface EventPublisher {
    void publish(String eventType, String payload) throws Exception;
}
