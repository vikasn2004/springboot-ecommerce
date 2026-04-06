package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.OrderItem;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.mapper.orderMapper;
import com.vikas.ecommerce.repository.OrderItemRepository;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
    //inject orderitem repo
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private orderMapper ordermapper;



    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
    User user=userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(()->new RuntimeException("User not found"));
    Order order=new Order();
    order.setUser(user);
    Order savedOrder=orderRepository.save(order);
    double totalPrice=0;
    List<OrderItem> orderItemDTOS=new ArrayList<>();
    for(OrderItemDTO items : orderRequestDTO.getItems()){
        Product product=productRepository.findById(items.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
        OrderItem item=new OrderItem();
        item.setProduct(product);
        item.setOrder(savedOrder);
        item.setQuantity(items.getQuantity());
        item.setPrice(product.getPrice());
      double amount =product.getPrice()*item.getQuantity();
        totalPrice+=amount;
        orderItemDTOS.add(item);
    }
     orderItemRepository.saveAll(orderItemDTOS);
        savedOrder.setOrderItems(orderItemDTOS);
        savedOrder.setTotalPrice(totalPrice);
        orderRepository.save(savedOrder);
    return ordermapper.convertTODTO(savedOrder);
    }
    @Override
    public List<OrderResponseDTO> getOrder(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> {
                    OrderResponseDTO orderResponseDTO=new OrderResponseDTO();
                    orderResponseDTO.setOrderId(order.getId());
                    orderResponseDTO.setCreatedAt(order.getCreatedAt());
                    orderResponseDTO.setTotalPrice(order.getTotalPrice());

                    List<OrderItemDTO>  items = order.getOrderItems()
                            .stream().map(
                                    item->{
                                        OrderItemDTO itemDTO=new OrderItemDTO();
                                        itemDTO.setProductId(item.getProduct().getId());
                                        itemDTO.setPrice(item.getProduct().getPrice());
                                        itemDTO.setQuantity(item.getQuantity());
                                        return itemDTO;
                                    })
                            .toList();
                    orderResponseDTO.setOrderItems(items);
                    return orderResponseDTO;
                           } )
                .toList();
    }
}
