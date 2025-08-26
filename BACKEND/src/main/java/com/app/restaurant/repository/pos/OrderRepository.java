package com.app.restaurant.repository.pos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.restaurant.enums.OrderStatus;
import com.app.restaurant.model.pos.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByStatus(OrderStatus status);

    List<Order> findAllByOrderDateAndStatus(LocalDate orderDate, OrderStatus status);

    List<Order> findAllByOrderDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, OrderStatus status);

    long countByOrderDateAndStatus(LocalDate orderDate, OrderStatus status);

    long countByOrderDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, OrderStatus status);

    List<Order> findAllByCustomerIdAndStatus(Integer customerId, OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.status = :status ORDER BY o.orderDate DESC, o.orderTime DESC")
    List<Order> findByStatusOrderByOrderDateDescOrderTimeDesc(
            @Param("status") OrderStatus status,
            Pageable pageable);
}