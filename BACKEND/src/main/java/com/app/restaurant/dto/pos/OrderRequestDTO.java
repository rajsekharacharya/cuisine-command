package com.app.restaurant.dto.pos;

import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.enums.OrderType;
import com.app.restaurant.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderRequestDTO {
    private Integer customerId;
    private OrderStatus status = OrderStatus.COMPLETED;
    private OrderType orderType;
    private BigDecimal subtotal;
    private BigDecimal totalQuantity;
    private BigDecimal discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal deliveryCharge;
    private BigDecimal containerCharge;
    private BigDecimal taxPercent;
    private BigDecimal taxAmount;
    private BigDecimal roundOff;
    private BigDecimal finalTotal;
    private String tableNumber;
    private PaymentMethod paymentMethod = PaymentMethod.CASH;

    @NotEmpty
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class OrderItemDTO {
        @Positive
        private Integer itemId;
        private Integer variationId;
        @Positive
        private Integer quantity;
        private BigDecimal subtotal;
        private List<OrderItemAddonDTO> addons = new ArrayList<>();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class OrderItemAddonDTO {
        private Integer id; // Optional for new add-ons
        @NotEmpty
        private String name;
        @Positive
        private BigDecimal price;
        private boolean status = true;
        private Integer variationId; // Optional, for variation-level add-ons
    }
}