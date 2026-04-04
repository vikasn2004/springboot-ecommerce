package com.vikas.ecommerce.DTO;

import com.vikas.ecommerce.entities.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    private long userId;
    private List<OrderItemDTO> items;
}
