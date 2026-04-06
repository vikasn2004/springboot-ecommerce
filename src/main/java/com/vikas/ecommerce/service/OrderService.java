package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;

import java.util.List;

public interface OrderService {
      List<OrderResponseDTO> getOrder(Long userId ) ;


    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);
}
