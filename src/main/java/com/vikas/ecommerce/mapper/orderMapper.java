package com.vikas.ecommerce.mapper;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.OrderItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class orderMapper {
    private final ModelMapper modelMapper ;
    public orderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderResponseDTO convertTODTO(Order savedOrder) {
        OrderResponseDTO dto= modelMapper.map(savedOrder, OrderResponseDTO.class);
        System.out.println("Order ID: " + savedOrder.getId());
        System.out.println("OrderItems size from entity: " + savedOrder.getOrderItems().size());
        List<OrderItemDTO> items = savedOrder.getOrderItems()
                .stream()
                .map(item -> {
                    OrderItemDTO orderItemDTO =new OrderItemDTO();
                    orderItemDTO.setProductId(item.getProduct().getId());
                    orderItemDTO.setQuantity(item.getQuantity());
                    double price=item.getProduct().getPrice()*item.getQuantity();
                    orderItemDTO.setPrice(price);
                    return orderItemDTO;
                })
                .toList();
        System.out.println("Items built: " + items.size());
      dto.setOrderItems(items);
      return dto;
    }
}
