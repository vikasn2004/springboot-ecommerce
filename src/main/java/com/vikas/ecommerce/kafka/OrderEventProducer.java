package com.vikas.ecommerce.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderEvent(Long ordeId,String status){
        String message="orderId: " + ordeId + " , status: " + status;
        kafkaTemplate.send("order-placed", message);
        System.out.println("Publishing order event: " + message);
    }
}
