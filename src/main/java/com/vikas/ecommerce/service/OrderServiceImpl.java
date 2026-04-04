package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.OrderItem;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.repository.OrderItemRepository;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    //inject order repo
    private final OrderRepository orderRepository;
    //inject user rep
    private final UserRepository userRepository;
    //inject product repo
    private final ProductRepository productRepository;
    //inject orderitem repo
    private final OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        double price=0;
       User user=userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Order order = new Order();
        order.setUser(user);
        Order savedOrder=orderRepository.save(order);
        double totalPrice=0;
        for(OrderItemDTO itemDTO : orderRequestDTO.getItems()){
            long productId = itemDTO.getProductId();
            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
            System.out.println(productId);
            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            totalPrice+=product.getPrice()*itemDTO.getQuantity();
            orderItemRepository.save(orderItem);
        }
        savedOrder.setTotalPrice(totalPrice);
       return orderRepository.save(savedOrder);
    }


}
