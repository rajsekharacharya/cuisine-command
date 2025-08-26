package com.app.restaurant.service.pos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.restaurant.dto.pos.OrderDTO;
import com.app.restaurant.dto.pos.OrderRequestDTO;
import com.app.restaurant.dto.pos.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO dto);

    OrderResponseDTO updateOrder(Integer orderId, OrderRequestDTO dto);

    OrderResponseDTO getOrderById(Integer orderId);

    List<OrderResponseDTO> getHeldOrders();

    OrderResponseDTO finalizeOrder(Integer orderId, OrderRequestDTO dto);

    String cancelOrder(Integer orderId);

    Page<OrderDTO> getAllOrder(String search, Boolean unpaged,
            Pageable pageable);
}