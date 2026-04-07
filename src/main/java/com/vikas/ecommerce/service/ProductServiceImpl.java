package com.vikas.ecommerce.service;

import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
