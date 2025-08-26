package com.app.restaurant.dto.pos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.enums.OrderType;
import com.app.restaurant.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private Integer id;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private String customer;
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
    private String createdBy;
    private String lastModifiedBy;

}
