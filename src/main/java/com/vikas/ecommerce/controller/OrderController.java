package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    //inject order service
    private final OrderService orderService;

    @PostMapping("/orders")
    public Order createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);
    }

}
