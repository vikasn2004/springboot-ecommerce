package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.ProductAddDTO;
import com.vikas.ecommerce.DTO.ProductResponseDTO;
import com.vikas.ecommerce.DTO.ProductUpdateDTO;
import com.vikas.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce")
public class ProductController {

    //inject the product service
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductAddDTO productAddDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productAddDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProduct(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue="10") int size){
        return ResponseEntity.ok(productService.getAllProducts(page,size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

        @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductUpdateDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateDTO productUpdateDTO){
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
          productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
