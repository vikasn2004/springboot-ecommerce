package com.vikas.ecommerce.DTO;

import com.vikas.ecommerce.entities.Category;
import com.vikas.ecommerce.entities.OrderItem;

import lombok.Data;

import java.util.List;

@Data
public class ProductAddDTO {
        String name;
        String description;
        double price;
        String brand;
        Long stockQuantity = 0L;
        List<OrderItem> orderItem;
        Category category;
        boolean isActive;
    }

