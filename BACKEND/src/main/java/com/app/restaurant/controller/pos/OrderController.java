package com.app.restaurant.controller.pos;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.configuration.MessageConstants;
import com.app.restaurant.dto.pos.OrderDTO;
import com.app.restaurant.dto.pos.OrderRequestDTO;
import com.app.restaurant.dto.pos.OrderResponseDTO;
import com.app.restaurant.response.ApiResponse;
import com.app.restaurant.service.pos.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pos/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderDTO>>> getAllOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "false") Boolean unpaged,
            @ParameterObject Pageable pageable) {
        Page<OrderDTO> orders = orderService.getAllOrder(search, unpaged, pageable);
        return ResponseEntity.ok(ApiResponse.<Page<OrderDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(orders)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@RequestBody OrderRequestDTO dto) {
        OrderResponseDTO created = orderService.createOrder(dto);
        return ResponseEntity.ok(ApiResponse.<OrderResponseDTO>builder()
                .status(true)
                .message(MessageConstants.CREATED_SUCCESSFULLY)
                .error(null)
                .data(created)
                .build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO updated = orderService.updateOrder(orderId, dto);
        return ResponseEntity.ok(ApiResponse.<OrderResponseDTO>builder()
                .status(true)
                .message(MessageConstants.UPDATED_SUCCESSFULLY)
                .error(null)
                .data(updated)
                .build());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrderById(@PathVariable Integer orderId) {
        OrderResponseDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.<OrderResponseDTO>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(order)
                .build());
    }

    @GetMapping("/held")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getHeldOrders() {
        List<OrderResponseDTO> heldOrders = orderService.getHeldOrders();
        return ResponseEntity.ok(ApiResponse.<List<OrderResponseDTO>>builder()
                .status(true)
                .message(MessageConstants.FETCHED_SUCCESSFULLY)
                .error(null)
                .data(heldOrders)
                .build());
    }

    @PatchMapping("/{orderId}/finalize")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> finalizeOrder(
            @PathVariable Integer orderId,
            @RequestBody OrderRequestDTO dto) {
        OrderResponseDTO finalized = orderService.finalizeOrder(orderId, dto);
        return ResponseEntity.ok(ApiResponse.<OrderResponseDTO>builder()
                .status(true)
                .message("Order finalized successfully")
                .error(null)
                .data(finalized)
                .build());
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable Integer orderId) {
        String msg = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .status(true)
                .message(msg)
                .error(null)
                .data(msg)
                .build());
    }
}