package com.vikas.ecommerce.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    Long orderId;
    LocalDateTime createdAt;
    List<OrderItemDTO> orderItems;
    double totalPrice;

}
