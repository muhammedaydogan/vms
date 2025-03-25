//package com.vm.purchase.infrastructure.messaging.consumer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class PurchaseRollbackConsumer {
//    private final ObjectMapper objectMapper;
//    //    private final UserRepository userRepository;
//    private final  vendingMachineRepository;
//
//    @RabbitListener(queues = "vending.event.purchase-rollback")
//
//}
