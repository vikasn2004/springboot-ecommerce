package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce")
public class OrderController {
    //inject order service
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequestDTO));
    }
    @GetMapping("/orders/all")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrder(userId));
    }


}
