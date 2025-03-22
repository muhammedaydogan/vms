package com.vm.vending.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Contains config;<br><strong>DLX (Dead Letter Excahnge)</strong>,<br> <strong>queue</strong><br> <strong>exchange</strong>
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange("vending.events");
    }

    @Bean
    public DirectExchange dlqExchange() {
        return new DirectExchange("vending.dlq");
    }

    @Bean
    public Queue productPurchasedQueue() {
        return QueueBuilder.durable("ProductPurchasedEvent")
                .withArgument("x-dead-letter-exchange", "vending.dlq")
                .withArgument("x-dead-letter-routing-key", "event.dlq")
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable("event.dlq").build();
    }

    @Bean
    public Binding productPurchasedBinding() {
        return BindingBuilder.bind(productPurchasedQueue())
                .to(mainExchange())
                .with("ProductPurchasedEvent");
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlqQueue())
                .to(dlqExchange())
                .with("event.dlq");
    }
}
