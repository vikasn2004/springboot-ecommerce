package com.vikas.ecommerce;

import com.vikas.ecommerce.DTO.OrderItemDTO;
import com.vikas.ecommerce.DTO.OrderRequestDTO;
import com.vikas.ecommerce.DTO.OrderResponseDTO;
import com.vikas.ecommerce.entities.Order;
import com.vikas.ecommerce.entities.Product;
import com.vikas.ecommerce.entities.User;
import com.vikas.ecommerce.exceptions.ResourceNotFoundExceptions;
import com.vikas.ecommerce.mapper.orderMapper;
import com.vikas.ecommerce.repository.OrderRepository;
import com.vikas.ecommerce.repository.ProductRepository;
import com.vikas.ecommerce.repository.UserRepository;
import com.vikas.ecommerce.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private orderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderService;
    private User user;
    private Product product;
    private Order order;
    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1000.0);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotalPrice(2000.0);

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(1L);
        orderResponseDTO.setTotalPrice(2000.0);
    }

    @Test
    void createOrder_success() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setItems(List.of(orderItemDTO));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.convertTODTO(order)).thenReturn(orderResponseDTO);

        OrderResponseDTO result = orderService.createOrder(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(1L);
        assertThat(result.getTotalPrice()).isEqualTo(2000.0);
        verify(orderRepository, times(1)).save(any(Order.class));

    }

    @Test
    void createOrder_shouldThrowException_whenUserNotFound() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(2);
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(99L);
        requestDTO.setItems(List.of(orderItemDTO));

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(ResourceNotFoundExceptions.class)
                .hasMessage("User not found");

        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_shouldThrowException_whenProductNotFound() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(5L);
        orderItemDTO.setQuantity(2);
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setItems(List.of(orderItemDTO));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(5L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> orderService.createOrder(requestDTO))
                .isInstanceOf(ResourceNotFoundExceptions.class)
                .hasMessage("Product not found");
        verify(orderRepository, never()).save(any());

    }

    @Test
    void createOrder_shouldCalculateTotalPriceCorrectly() {
        product.setPrice(1000.0);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(1L);
        orderItemDTO.setQuantity(3);
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setItems(List.of(orderItemDTO));

        Order saveOrder = new Order();
        saveOrder.setUser(user);
        saveOrder.setTotalPrice(3000.0);
        orderResponseDTO.setTotalPrice(3000.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(saveOrder);
        when(orderMapper.convertTODTO(saveOrder)).thenReturn(orderResponseDTO);


        OrderResponseDTO result = orderService.createOrder(requestDTO);
        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo(3000.0);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getAllOrders_shouldReturnListOfOrderResponses() {
        Order order2 = new Order();
        order2.setId(2L);

        OrderResponseDTO dto2 = new OrderResponseDTO();
        dto2.setOrderId(2L); // ← use correct field name

        when(orderRepository.findAll()).thenReturn(List.of(order, order2));
        when(orderMapper.convertTODTO(order)).thenReturn(orderResponseDTO);
        when(orderMapper.convertTODTO(order2)).thenReturn(dto2);

        List<OrderResponseDTO> result = orderService.getAllOrders();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getOrderId()).isEqualTo(1L);
        assertThat(result.get(1).getOrderId()).isEqualTo(2L);
    }
    @Test
    void cancelOrder_shouldDeleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(order);
        String message=orderService.cancelOrder(1L);
        assertThat(message).isEqualTo("Order has been deleted");
        verify(orderRepository, times(1)).delete(order);
    }
    @Test
    void cancelOrder_shouldThrowException_whenOrderNotFound() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());
      assertThatThrownBy(() -> orderService.cancelOrder(2L))
              .isInstanceOf(ResourceNotFoundExceptions.class)
              .hasMessage("Order not found");
        verify(orderRepository, times(0)).delete(any());
    }
    @Test
    void getOrder_shouldReturnOrdersForUser() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(order));
        when(orderMapper.convertTODTO(order)).thenReturn(orderResponseDTO);

        List<OrderResponseDTO> result = orderService.getOrder(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getOrderId()).isEqualTo(1L);
    }
    @Test
    void getOrder_shouldReturnEmptyList_whenUserHasNoOrders() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of());

        List<OrderResponseDTO> result = orderService.getOrder(1L);

        assertThat(result).isEmpty();
    }

}