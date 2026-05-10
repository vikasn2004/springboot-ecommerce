package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.OrderItem;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.kafka.OrderEventProducer;
import com.vikas.ecommerce.mapper.OrderMapper;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
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
    private final OrderMapper ordermapper;
     private final OrderEventProducer orderEventProducer;

    @Transactional
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO)  {
    User user=userRepository.findById(orderRequestDTO.getUserId()).orElseThrow(()->new ResourceNotFoundExceptions("User not found"));
    Order order=new Order();
    order.setUser(user);
    double totalPrice=0;
    List<OrderItem> orderItems=new ArrayList<>();
    for(OrderItemDTO items : orderRequestDTO.getItems()){
        Product product=productRepository.findById(items.getProductId()).orElseThrow(()->new ResourceNotFoundExceptions("Product not found"));
        if(product.getStockQuantity()<items.getQuantity()){
            throw new IllegalArgumentException(
                    "Insufficient Stock Quantity " + product.getName()+
                    " Stock left in ware " + product.getStockQuantity() +
                            " stock required " + items.getQuantity()
            );
        }
        product.setStockQuantity(product.getStockQuantity()-items.getQuantity());
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
      log.info("Order created for user {} with id:{}",user.getId(),savedOrder.getId());
      orderEventProducer.publishOrderEvent(savedOrder.getId(),"PLACED");
      return ordermapper.convertTODTO(savedOrder);
    }
    @Transactional(readOnly = true)
    @Override
    public  List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(ordermapper::convertTODTO)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public String cancelOrder(Long orderId) {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundExceptions("Order not found"));
        order.setActive(false);
        for(OrderItem item : order.getOrderItems()) {
            Product product= item.getProduct();
            product.setStockQuantity(product.getStockQuantity()+item.getQuantity());
            productRepository.save(product);
        }
        orderRepository.save(order);
        log.info("Order {} has been cancelled", orderId);
        return "Order has been cancelled";
    }

    @Transactional( readOnly = true)
    @Override
    public List<OrderResponseDTO> getOrder(Long userId) {
         List<Order> orders =orderRepository.findByUserId(userId);
               return orders.stream().map(order -> ordermapper.convertTODTO(order))
                       .toList();

    }
}
