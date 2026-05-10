package com.vikas.ecommerce.mapper;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class OrderMapper {
    private final ModelMapper modelMapper ;
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderResponseDTO convertTODTO(Order savedOrder) {
        OrderResponseDTO dto= modelMapper.map(savedOrder, OrderResponseDTO.class);
        log.debug("Mapping order ID: {}, items: {}", savedOrder.getId(), savedOrder.getOrderItems().size());
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
      dto.setOrderItems(items);
      return dto;
    }
}
