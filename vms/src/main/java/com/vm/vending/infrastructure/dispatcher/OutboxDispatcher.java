package com.vm.vending.infrastructure.dispatcher;

import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import com.vm.vending.infrastructure.outbox.repository.OutboxMessageRepository;
import com.vm.vending.infrastructure.messaging.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxDispatcher {
    private static final int MAX_RETRY_COUNT = 3;
    private final OutboxMessageRepository outboxMessageRepository;
    private final EventPublisher eventPublisher;
    private final RabbitTemplate rabbitTemplate;


    @Scheduled(fixedDelay = 5000) // Todo enable/disable and configure from config. make custom annotation for this
    public void dispatchOutboxEvents() {
        List<OutboxMessageEntity> messages = outboxMessageRepository.findAll()
                .stream()
                .filter(msg -> msg.isPending())
                .toList();

        for (OutboxMessageEntity message : messages) {
            try {
                eventPublisher.publish(message.getEventType(), message.getPayload());
                message.markAsSent();
                log.info("Outbox event published successfully. id={}", message.getId());
                //  todo duruma gore vakit kalirsa saga pattern'e gecilebilir
            } catch (Exception e) {
                if (message.isRetryLimitExceeded(MAX_RETRY_COUNT)) {
                    message.markAsDead(); // todo dead mesajlari ne  zaman silinecek
                    log.error("Max retry limit reached. Marking message as DEAD. id={}", message.getId());

                    //DLQ Fallback
                    rabbitTemplate.convertAndSend("vending.dlq", "event.dlq", message.getPayload());

                    // todo make compensating event
                } else {
                    message.markAsFailed();
                    log.warn("Failed to publish event. Will retry. id={}, retryCount={}", message.getId(), message.getRetryCount());
                }
            }
        }

        outboxMessageRepository.saveAll(messages);
    }

    @Scheduled(fixedDelay = 604_800_000) // 7 days
    public void cleanDeadMessages() {
        // todo
    }
}
