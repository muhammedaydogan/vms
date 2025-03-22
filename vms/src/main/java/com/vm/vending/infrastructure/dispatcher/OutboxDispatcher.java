package com.vm.vending.infrastructure.dispatcher;

import com.vm.vending.infrastructure.outbox.entity.OutboxMessageEntity;
import com.vm.vending.infrastructure.outbox.repository.OutboxMessageRepository;
import com.vm.vending.infrastructure.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxDispatcher {
    private final OutboxMessageRepository outboxMessageRepository;
    private final EventPublisher eventPublisher;


    @Scheduled(fixedDelay = 5000) // Todo enable/disable and configure from config. make custom annotation for this
    public void dispatchOutboxEvents() {
        List<OutboxMessageEntity> messages = outboxMessageRepository.findAll()
                .stream()
                .filter(msg -> msg.getStatus().equals("NEW"))
                .toList();

        for (OutboxMessageEntity message : messages) {
            try {
                eventPublisher.publish(message.getEventType(), message.getPayload());
                message.setStatus("SENT");
                //  todo duruma gore vakit kalirsa saga pattern'e gecilebilir
            } catch (Exception e) {
                log.error("Failed to publish outbox message with id: {}", message.getId(), e);
                message.setStatus("FAILED");
            }
        }

        outboxMessageRepository.saveAll(messages);
    }
}
