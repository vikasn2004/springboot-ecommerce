package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.ProductAddDTO;
import com.vikas.ecommerce.DTO.ProductResponseDTO;
import com.vikas.ecommerce.DTO.ProductUpdateDTO;
import com.vikas.ecommerce.entities.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductAddDTO productAddDTO) ;
    Page<ProductResponseDTO> getAllProducts(int page, int size);
    List<ProductResponseDTO> getProductsByCategory(Long categoryId);
    ProductResponseDTO getProductById(Long id);
    ProductUpdateDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO);
    void deleteProduct(Long id);
}
