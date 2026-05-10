package com.vikas.ecommerce.DTO;

import com.vikas.ecommerce.entities.Category;
import lombok.Data;

@Data
public class ProductResponseDTO {
    Long id;
    String name;
    String description;
    double price;
    String brand;
    Long stockQuantity = 0L;
    Category category;
    boolean isActive;
}
