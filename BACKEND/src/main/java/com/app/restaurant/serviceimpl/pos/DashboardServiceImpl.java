package com.app.restaurant.serviceimpl.pos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.enums.OrderType;
import com.app.restaurant.enums.PaymentMethod;
import com.app.restaurant.model.pos.Order;
import com.app.restaurant.model.pos.OrderItem;
import com.app.restaurant.repository.pos.OrderRepository;
import com.app.restaurant.service.pos.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

        private final OrderRepository orderRepository;

        @Override
        public Map<String, Object> getDashboardData(LocalDate startDate, LocalDate endDate) {
                Map<String, Object> dashboardData = new HashMap<>();
                LocalDate today = LocalDate.now();

                if (startDate == null || endDate == null) {
                        startDate = today;
                        endDate = today;
                }

                dashboardData.put("revenue", getRevenueData(today));
                dashboardData.put("completedOrders", getOrderCountByStatus(today, OrderStatus.COMPLETED));
                dashboardData.put("canceledOrders", getOrderCountByStatus(today, OrderStatus.CANCELLED));
                dashboardData.put("topSellingDishes", getTopSellingDishes(startDate, endDate));
                dashboardData.put("recentOrders", getRecentOrders(3));
                dashboardData.put("orderTypeBreakdown", getOrderTypeBreakdown(startDate, endDate));
                dashboardData.put("averageOrderValue", getAverageOrderValue(today));
                dashboardData.put("customerRetention", getCustomerRetention(startDate, endDate));
                dashboardData.put("peakHours", getPeakHours(today));
                dashboardData.put("paymentMethodDistribution", getPaymentMethodDistribution(startDate, endDate));
                dashboardData.put("discountImpact", getDiscountImpact(startDate, endDate));

                return dashboardData;
        }

        private Map<String, BigDecimal> getRevenueData(LocalDate today) {
                Map<String, BigDecimal> revenue = new HashMap<>();

                revenue.put("today", orderRepository.findAllByOrderDateAndStatus(today, OrderStatus.COMPLETED)
                                .stream()
                                .map(Order::getFinalTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                revenue.put("week", orderRepository
                                .findAllByOrderDateBetweenAndStatus(weekStart, today, OrderStatus.COMPLETED)
                                .stream()
                                .map(Order::getFinalTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
                revenue.put("month", orderRepository
                                .findAllByOrderDateBetweenAndStatus(monthStart, today, OrderStatus.COMPLETED)
                                .stream()
                                .map(Order::getFinalTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                LocalDate yearStart = today.with(TemporalAdjusters.firstDayOfYear());
                revenue.put("year", orderRepository
                                .findAllByOrderDateBetweenAndStatus(yearStart, today, OrderStatus.COMPLETED)
                                .stream()
                                .map(Order::getFinalTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add));

                return revenue;
        }

        private Map<String, Long> getOrderCountByStatus(LocalDate today, OrderStatus status) {
                Map<String, Long> orderCount = new HashMap<>();

                orderCount.put("today", orderRepository.countByOrderDateAndStatus(today, status));
                LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                orderCount.put("week", orderRepository.countByOrderDateBetweenAndStatus(weekStart, today, status));
                LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
                orderCount.put("month", orderRepository.countByOrderDateBetweenAndStatus(monthStart, today, status));
                LocalDate yearStart = today.with(TemporalAdjusters.firstDayOfYear());
                orderCount.put("year", orderRepository.countByOrderDateBetweenAndStatus(yearStart, today, status));

                return orderCount;
        }

        private List<Map<String, Object>> getTopSellingDishes(LocalDate startDate, LocalDate endDate) {
                Map<String, Long> dishSales = orderRepository
                                .findAllByOrderDateBetweenAndStatus(startDate, endDate, OrderStatus.COMPLETED)
                                .stream()
                                .flatMap(order -> order.getOrderItems().stream())
                                .collect(Collectors.groupingBy(
                                                orderItem -> orderItem.getItem().getName(),
                                                Collectors.summingLong(OrderItem::getQuantity)));

                return dishSales.entrySet().stream()
                                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                                .limit(5)
                                .map(entry -> {
                                        Map<String, Object> dish = new HashMap<>();
                                        dish.put("name", entry.getKey());
                                        dish.put("quantitySold", entry.getValue());
                                        return dish;
                                })
                                .collect(Collectors.toList());
        }

        private List<Map<String, Object>> getRecentOrders(int limit) {
                return orderRepository
                                .findByStatusOrderByOrderDateDescOrderTimeDesc(OrderStatus.COMPLETED,
                                                PageRequest.of(0, limit))
                                .stream()
                                .map(order -> {
                                        Map<String, Object> orderData = new HashMap<>();
                                        orderData.put("id", order.getId());
                                        orderData.put("orderDate", order.getOrderDate().toString());
                                        orderData.put("orderTime", order.getOrderTime().toString());
                                        orderData.put("finalTotal", order.getFinalTotal());
                                        orderData.put("customerName",
                                                        order.getCustomer() != null ? order.getCustomer().getName()
                                                                        : "Guest");
                                        orderData.put("orderType", order.getOrderType().name());
                                        return orderData;
                                })
                                .collect(Collectors.toList());
        }

        private Map<String, Double> getOrderTypeBreakdown(LocalDate startDate, LocalDate endDate) {
                List<Order> orders = orderRepository.findAllByOrderDateBetweenAndStatus(startDate, endDate,
                                OrderStatus.COMPLETED);
                long totalOrders = orders.size();
                if (totalOrders == 0) {
                        return Map.of("PICKUP", 0.0, "DELIVERY", 0.0);
                }

                Map<OrderType, Long> typeCounts = orders.stream()
                                .collect(Collectors.groupingBy(Order::getOrderType, Collectors.counting()));

                Map<String, Double> breakdown = new HashMap<>();
                breakdown.put("PICKUP", (typeCounts.getOrDefault(OrderType.PICKUP, 0L) * 100.0) / totalOrders);
                breakdown.put("DINE_IN", (typeCounts.getOrDefault(OrderType.DINE_IN, 0L) * 100.0) / totalOrders);
                breakdown.put("DELIVERY", (typeCounts.getOrDefault(OrderType.DELIVERY, 0L) * 100.0) / totalOrders);
                return breakdown;
        }

        private Map<String, BigDecimal> getAverageOrderValue(LocalDate today) {
                Map<String, BigDecimal> aov = new HashMap<>();

                List<Order> todayOrders = orderRepository.findAllByOrderDateAndStatus(today, OrderStatus.COMPLETED);
                aov.put("today", todayOrders.isEmpty() ? BigDecimal.ZERO
                                : todayOrders.stream()
                                                .map(Order::getFinalTotal)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                                .divide(BigDecimal.valueOf(todayOrders.size()), 2,
                                                                RoundingMode.HALF_UP));

                LocalDate weekStart = today.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                List<Order> weekOrders = orderRepository.findAllByOrderDateBetweenAndStatus(weekStart, today,
                                OrderStatus.COMPLETED);
                aov.put("week", weekOrders.isEmpty() ? BigDecimal.ZERO
                                : weekOrders.stream()
                                                .map(Order::getFinalTotal)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                                .divide(BigDecimal.valueOf(weekOrders.size()), 2,
                                                                RoundingMode.HALF_UP));

                LocalDate monthStart = today.with(TemporalAdjusters.firstDayOfMonth());
                List<Order> monthOrders = orderRepository.findAllByOrderDateBetweenAndStatus(monthStart, today,
                                OrderStatus.COMPLETED);
                aov.put("month", monthOrders.isEmpty() ? BigDecimal.ZERO
                                : monthOrders.stream()
                                                .map(Order::getFinalTotal)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                                .divide(BigDecimal.valueOf(monthOrders.size()), 2,
                                                                RoundingMode.HALF_UP));

                LocalDate yearStart = today.with(TemporalAdjusters.firstDayOfYear());
                List<Order> yearOrders = orderRepository.findAllByOrderDateBetweenAndStatus(yearStart, today,
                                OrderStatus.COMPLETED);
                aov.put("year", yearOrders.isEmpty() ? BigDecimal.ZERO
                                : yearOrders.stream()
                                                .map(Order::getFinalTotal)
                                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                                .divide(BigDecimal.valueOf(yearOrders.size()), 2,
                                                                RoundingMode.HALF_UP));

                return aov;
        }

        private Map<String, Long> getCustomerRetention(LocalDate startDate, LocalDate endDate) {
                List<Order> orders = orderRepository.findAllByOrderDateBetweenAndStatus(startDate, endDate,
                                OrderStatus.COMPLETED);
                Set<Integer> customerIds = orders.stream()
                                .filter(order -> order.getCustomer() != null)
                                .map(order -> order.getCustomer().getId())
                                .collect(Collectors.toSet());

                long repeatCustomers = customerIds.stream()
                                .filter(id -> {
                                        List<Order> customerOrders = orderRepository.findAllByCustomerIdAndStatus(id,
                                                        OrderStatus.COMPLETED);
                                        return customerOrders.stream()
                                                        .anyMatch(o -> o.getOrderDate().isBefore(startDate));
                                })
                                .count();

                long newCustomers = customerIds.size() - repeatCustomers;

                return Map.of("newCustomers", newCustomers, "repeatCustomers", repeatCustomers);
        }

        private Map<Integer, Long> getPeakHours(LocalDate date) {
                List<Order> orders = orderRepository.findAllByOrderDateAndStatus(date, OrderStatus.COMPLETED);
                Map<Integer, Long> hourlyCounts = orders.stream()
                                .collect(Collectors.groupingBy(
                                                order -> order.getOrderTime().getHour(),
                                                Collectors.counting()));

                Map<Integer, Long> peakHours = new TreeMap<>();
                for (int hour = 0; hour < 24; hour++) {
                        peakHours.put(hour, hourlyCounts.getOrDefault(hour, 0L));
                }
                return peakHours;
        }

        private Map<String, Double> getPaymentMethodDistribution(LocalDate startDate, LocalDate endDate) {
                List<Order> orders = orderRepository.findAllByOrderDateBetweenAndStatus(startDate, endDate,
                                OrderStatus.COMPLETED);
                long totalOrders = orders.size();
                if (totalOrders == 0) {
                        return Map.of("CASH", 0.0, "CARD", 0.0, "UPI", 0.0);
                }

                Map<PaymentMethod, Long> paymentCounts = orders.stream()
                                .filter(order -> order.getPaymentMethod() != null)
                                .collect(Collectors.groupingBy(Order::getPaymentMethod, Collectors.counting()));

                Map<String, Double> distribution = new HashMap<>();
                distribution.put("CASH", (paymentCounts.getOrDefault(PaymentMethod.CASH, 0L) * 100.0) / totalOrders);
                distribution.put("CARD", (paymentCounts.getOrDefault(PaymentMethod.CARD, 0L) * 100.0) / totalOrders);
                distribution.put("UPI", (paymentCounts.getOrDefault(PaymentMethod.UPI, 0L) * 100.0) / totalOrders);
                return distribution;
        }

        private Map<String, Object> getDiscountImpact(LocalDate startDate, LocalDate endDate) {
                List<Order> orders = orderRepository.findAllByOrderDateBetweenAndStatus(startDate, endDate,
                                OrderStatus.COMPLETED);
                BigDecimal totalDiscount = orders.stream()
                                .map(Order::getDiscountAmount)
                                .filter(Objects::nonNull)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal totalRevenue = orders.stream()
                                .map(Order::getFinalTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                double discountPercentage = totalRevenue.compareTo(BigDecimal.ZERO) > 0
                                ? totalDiscount.divide(totalRevenue, 4, RoundingMode.HALF_UP)
                                                .multiply(BigDecimal.valueOf(100)).doubleValue()
                                : 0.0;

                Map<String, Object> discountImpact = new HashMap<>();
                discountImpact.put("totalDiscount", totalDiscount);
                discountImpact.put("discountPercentage", discountPercentage);
                return discountImpact;
        }
}