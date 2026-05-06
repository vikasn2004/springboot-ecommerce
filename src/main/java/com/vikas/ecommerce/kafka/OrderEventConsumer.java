package com.vikas.ecommerce.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {
    @KafkaListener(topics = "order-placed",groupId = "ecommerce-group")
    public void consumerOrderPlaced(String message) {
        System.out.println("Order-Consumption: " + message);
    }
}

