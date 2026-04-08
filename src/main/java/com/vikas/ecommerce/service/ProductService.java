package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product) ;
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product updatedProduct);
    void deleteProduct(Long id);
}
