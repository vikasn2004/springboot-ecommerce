package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    //inject order service
    private final OrderService orderService;

    @PostMapping("/orders")
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);
    }

    @GetMapping("/orders/{userId}")
    public List<OrderResponseDTO> getAllOrders(@PathVariable Long userId){
        return orderService.getOrder(userId);
    }

}
