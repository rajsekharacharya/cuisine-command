package com.app.restaurant.dto.pos;

import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.enums.OrderType;
import com.app.restaurant.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderResponseDTO {
    private Integer id;
    @JsonFormat(pattern = "MMMM dd, yyyy") // e.g., "June 25, 2025"
    private LocalDate orderDate;

    @JsonFormat(pattern = "h:mm a") // e.g., "1:39 AM"
    private LocalTime orderTime;
    private CustomerDTO customer;
    private OrderStatus status;
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
    private PaymentMethod paymentMethod;
    private List<OrderItemDTO> orderItems;
    private BillDTO bill;
    private KotDTO kot;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class CustomerDTO {
        private Integer id;
        private String name;
        private String phone;
        private String email;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class OrderItemDTO {
        private Integer id;
        private Integer itemId;
        private String itemName;
        private Integer variationId;
        private String variationName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;
        private List<OrderItemAddonDTO> addons;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class OrderItemAddonDTO {
        private Integer id;
        private String name;
        private BigDecimal price;
        private boolean status;
        private Integer variationId; // Optional, for variation-level add-ons
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BillDTO {
        private Integer orderId;
        private String customerName;
        private List<BillItemDTO> items;
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
        private PaymentMethod paymentMethod;
        private String tableNumber;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BillItemDTO {
        private String itemName;
        private String variationName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
        private List<OrderItemAddonDTO> addons;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class KotDTO {
        private Integer orderId;
        private List<KotItemDTO> items;
        private String tableNumber;
        private OrderType orderType;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class KotItemDTO {
        private String itemName;
        private String variationName;
        private Integer quantity;
        private List<OrderItemAddonDTO> addons;
    }
}