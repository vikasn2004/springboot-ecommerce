package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product) ;
    Page<Product> getAllProducts(int page, int size);
    List<Product> getProductsByCategory(Long categoryId);
    Product getProductById(Long id);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
}
