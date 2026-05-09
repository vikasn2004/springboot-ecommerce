package com.vikas.ecommerce.DTO;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "Category name cannot be empty")
    private String name;
}
