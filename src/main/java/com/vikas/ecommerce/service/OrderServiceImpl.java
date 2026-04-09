package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.OrderItem;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.mapper.orderMapper;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    //inject order repo
    private final OrderRepository orderRepository;
    //inject user rep
    private final UserRepository userRepository;
    //inject product repo
    private final ProductRepository productRepository;
    private final orderMapper ordermapper;



    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO)  {
    User user=userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(()->new ResourceNotFoundExceptions("User not found"));
    Order order=new Order();
    order.setUser(user);
    double totalPrice=0;
    List<OrderItem> orderItems=new ArrayList<>();
    for(OrderItemDTO items : orderRequestDTO.getItems()){
        Product product=productRepository.findById(items.getProductId()).orElseThrow(()->new ResourceNotFoundExceptions("Product not found"));
        OrderItem item=new OrderItem();
        item.setProduct(product);
        item.setOrder(order);
        item.setQuantity(items.getQuantity());
        item.setPrice(product.getPrice());
      double amount =product.getPrice()*item.getQuantity();
        totalPrice+=amount;
        orderItems.add(item);
    }
      order.setOrderItems(orderItems);
      order.setTotalPrice(totalPrice);
      Order savedOrder=  orderRepository.save(order);
      return ordermapper.convertTODTO(savedOrder);
    }

    @Override
    public  List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(ordermapper::convertTODTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getOrder(Long userId) {
         List<Order> orders =orderRepository.findByUserId(userId);
               return orders.stream().map(order -> ordermapper.convertTODTO(order))
                       .toList();

    }
}
