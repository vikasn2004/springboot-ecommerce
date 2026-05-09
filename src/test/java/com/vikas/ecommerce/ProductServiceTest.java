package com.vikas.ecommerce;

import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setBrand("Apple");
        product.setName("Iphone 16");
        product.setDescription("256gb storage tcp display");
        product.setPrice(25000);
        product.setActive(true);
    }
    @Test
    public void createProduct_saveProduct_success() {
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.createProduct(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Iphone 16");
        verify(productRepository,times(1)).save(product);
    }
    @Test
    public void createProduct_saveProduct_fail() {
        product.setActive(false);
      assertThatThrownBy(()-> productService.createProduct(product)).isInstanceOf(ResourceNotFoundExceptions.class)
              .hasMessage("Product is not active");
      verify(productRepository,times(0)).save(product);
    }
    @Test
    public void getAllProducts_ReturnList(){
        Product product2 = new Product();
        product2.setId(2L);
        product2.setBrand("Samsung Galaxy");
        product2.setName("S26");
        product2.setDescription("Samsung Galaxy S26 Ultra");
        product2.setPrice(22000);
        product2.setActive(true);
        Page<Product> productPage = new PageImpl<>(List.of(product2,product));
        when(productRepository.findAll(PageRequest.of(0,10))).thenReturn(productPage);
        Page<Product> allProducts = productService.getAllProducts(0, 10);
        assertThat(allProducts).isNotNull();
        assertThat(allProducts.getContent()).hasSize(2);


    }
    @Test
    public void getAllProducts_ReturnEmptyList_WhenNoProductsFound() {
        Page<Product> allProducts =new PageImpl<>(List.of());
        when(productRepository.findAll(PageRequest.of(0,10))).thenReturn(allProducts);
        Page<Product> allProduct = productService.getAllProducts(0,10);
        assertThat(allProducts).isEmpty();
    }
    @Test
    public void getProductById_success_whenProductFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(1L);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getName()).isEqualTo("Iphone 16");
        verify(productRepository,times(1)).findById(1L);
    }
    @Test
    public void getProductById_fail_whenProductNotFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> productService.getProductById(2L))
                .isInstanceOf(ResourceNotFoundExceptions.class)
                .hasMessage("Product not found");
        verify(productRepository,times(1)).findById(2L);
    }
    @Test
    public void updateProduct_success_whenProductFound() {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setBrand("Apple");
        updatedProduct.setPrice(30000);
        updatedProduct.setActive(true);
        updatedProduct.setName("Iphone 16");
        updatedProduct.setDescription("256gb storage tcp display");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updatedProduct);

        Product savedProduct = productService.updateProduct(1L,updatedProduct);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Iphone 16");
        assertThat(savedProduct.getPrice()).isEqualTo(30000);
        verify(productRepository,times(1)).save(product);
    }
    @Test
    public void updateProduct_fail_whenProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setId(5L);
        updatedProduct.setBrand("Apple");
        updatedProduct.setPrice(30000);
        updatedProduct.setActive(true);
        updatedProduct.setName("Iphone 16");
        updatedProduct.setDescription("256gb storage tcp display");

        when(productRepository.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(5L, updatedProduct))
                .isInstanceOf(ResourceNotFoundExceptions.class)
                .hasMessage("Product not found");

        verify(productRepository, never()).save(any());
    }
    @Test
    void deleteProduct_shouldCallDeleteById() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
