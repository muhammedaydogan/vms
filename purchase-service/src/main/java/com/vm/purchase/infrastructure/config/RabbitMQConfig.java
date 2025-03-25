package com.vm.purchase.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange("vending.events");
    }

    @Bean
    public Queue purchaseConfirmedQueue() {
        return QueueBuilder.durable("PurchaseConfirmedEvent")
                .withArgument("x-dead-letter-exchange", "vending.dlq")
                .withArgument("x-dead-letter-routing-key", "event.dlq")
                .build();
    }

    @Bean
    public Binding purchaseConfirmedBinding() {
        return BindingBuilder.bind(purchaseConfirmedQueue())
                .to(mainExchange())
                .with("PurchaseConfirmedEvent");
    }
}