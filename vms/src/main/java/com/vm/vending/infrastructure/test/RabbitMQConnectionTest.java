//package com.vm.vending.infrastructure.test;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQConnectionTest implements CommandLineRunner {
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Override
//    public void run(String... args) throws Exception {
//        try {
//            rabbitTemplate.convertAndSend("testExchange", "testRouting", "Test message");
//            System.out.println("Bağlantı başarılı!");
//        } catch (Exception e) {
//            System.err.println("Bağlantı hatası: " + e.getMessage());
//        }
//    }
//}
