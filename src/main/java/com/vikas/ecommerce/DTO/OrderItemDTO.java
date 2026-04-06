package com.vikas.ecommerce.DTO;

import lombok.Data;

@Data
public class OrderItemDTO {
    private long productId;
    private long quantity;
    private double price;
}
