package com.vm.user.infrastructure.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vm.common.domain.event.ProductPurchasedEvent;
import com.vm.common.domain.valueobject.Money;
import com.vm.user.domain.model.User;
import com.vm.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductPurchasedEventConsumer {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @RabbitListener(queues = "ProductPurchasedEvent")
    public void consume(String payload) {
        try {
            ProductPurchasedEvent event = objectMapper.readValue(payload, ProductPurchasedEvent.class);
            log.info("ProductPurchasedEvent received in UserService: {}", event);

            UUID userId = event.getUserId();
            int price = event.getPrice();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            user.decreaseBalance(new Money(price));
            userRepository.save(user);

            log.info("User balance updated successfully for userId: {}, decreased amount: {}", user.getId(), price);
        } catch (Exception e) {
            log.error("Failed to process ProductPurchasedEvent", e);
            // DLQ'ya gitmesi için hata atıyorum
            throw new RuntimeException(e);
        }
    }
}
