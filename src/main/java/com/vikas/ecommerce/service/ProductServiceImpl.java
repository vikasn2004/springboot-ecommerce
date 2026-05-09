package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Category;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.repository.CategoryRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    //inject the product repo
    private final ProductRepository productRepository;
    //inject the category repo
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(Product product){

        if(!product.isActive()){
            throw new IllegalArgumentException("Product is not active");
        }
        Category category=categoryRepository.findById(product.getCategory().getId()).orElseThrow(()->new ResourceNotFoundExceptions("Category not found"));
        product.setCategory(category);
        return  productRepository.save(product) ;
    }

    @Override
    public Page<Product> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundExceptions("Product not found") );
        return product;
    }

    @Override
    public Product updateProduct(Long id,Product updatedProduct) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product not found"));
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setDescription(updatedProduct.getDescription());
        product.setActive(updatedProduct.isActive());
        product.setBrand(updatedProduct.getBrand());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
      productRepository.deleteById(id);
    }

}
