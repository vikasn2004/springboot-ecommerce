package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.ProductAddDTO;
import com.vikas.ecommerce.DTO.ProductResponseDTO;
import com.vikas.ecommerce.DTO.ProductUpdateDTO;
import com.vikas.ecommerce.entities.Category;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.repository.CategoryRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    //inject the product repo
    private final ProductRepository productRepository;
    //inject the category repo
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDTO createProduct(ProductAddDTO productAddDTO){
        Product product = modelMapper.map(productAddDTO, Product.class);

        if(!product.isActive()){
            throw new IllegalArgumentException("Product is not active");
        }
        Category category=categoryRepository.findById(product.getCategory().getId()).orElseThrow(()->new ResourceNotFoundExceptions("Category not found"));
        product.setCategory(category);
        Product saved= productRepository.save(product) ;
        return modelMapper.map(saved, ProductResponseDTO.class);
    }

    @Override
    public Page<ProductResponseDTO> getAllProducts(int page, int size) {
        Page<Product> products= productRepository.findAll(PageRequest.of(page,size));
        return products.map(product -> modelMapper.map(product, ProductResponseDTO.class));
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Category not found"));
        List<Product> found= productRepository.findByCategoryId(categoryId);

        return found.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow( () -> new ResourceNotFoundExceptions("Product not found") );
       return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public ProductUpdateDTO updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
           Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Product not found"));
        Product productMapped=modelMapper.map(productUpdateDTO, Product.class);
        product.setName(productMapped.getName());
        product.setPrice(productMapped.getPrice());
        product.setDescription(productMapped.getDescription());
        product.setActive(productMapped.isActive());
        product.setBrand(productMapped.getBrand());
        Product saved= productRepository.save(product);
        return modelMapper.map(saved, ProductUpdateDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
      productRepository.deleteById(id);
    }

}
