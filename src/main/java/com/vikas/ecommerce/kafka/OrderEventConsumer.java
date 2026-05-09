package com.vikas.ecommerce.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class OrderEventConsumer {
    @KafkaListener(topics = "order-placed",groupId = "ecommerce-group")
    public void consumerOrderPlaced(String message) {
        log.info("Received Order placed event {} : " ,message);
    }
}

