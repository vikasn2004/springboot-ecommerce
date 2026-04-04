package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    //inject the product service
    private final ProductService productService;

    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }
}
