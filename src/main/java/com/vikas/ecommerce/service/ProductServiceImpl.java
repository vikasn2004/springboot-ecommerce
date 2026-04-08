package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    //inject the product repo
    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product){

        if(!product.isActive()){
            throw new ResourceNotFoundExceptions("Product is not active");
        }

        return  productRepository.save(product) ;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundExceptions("Product not found") );
        return product;
    }

    @Override
    public Product updateProduct(Long id,Product updatedProduct) {
        Product product=productRepository.findById(id).get();
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
