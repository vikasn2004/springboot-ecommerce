package com.vikas.ecommerce.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderEvent(Long ordeId,String status){
        String message="orderId: " + ordeId + " , status: " + status;
        kafkaTemplate.send("order-placed", message);
        log.info("Published Order placed event {} : " ,message);

    }
}
