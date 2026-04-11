package com.vikas.ecommerce.controller;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce")
public class OrderController {
    //inject order service
    private final OrderService orderService;

    @PostMapping("/orders")
    @PreAuthorize("hasRole('ADMIN') or '@orderSecurityService.isOwner(authentication, #orderRequestDTO.userId)")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(orderRequestDTO));
    }

    @GetMapping("/orders/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/orders/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @orderSecurityService.isOwner(authentication, #userId)")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(@PathVariable Long userId){
        return ResponseEntity.ok(orderService.getOrder(userId));
    }
    @DeleteMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or @orderSecurityService.isOrderOwner(authentication, #orderId)")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("done deleted the recent order");
    }


}
