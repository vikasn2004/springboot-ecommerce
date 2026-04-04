package com.vikas.ecommerce.service;

import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.entities.Order;

public interface OrderService {
    Order createOrder(OrderRequestDTO orderRequestDTO);
}
