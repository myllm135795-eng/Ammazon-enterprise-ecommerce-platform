package com.ammazon.order.repository;

import com.ammazon.order.entity.Order;
import com.ammazon.commons.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUserId(String userId);
    List<Order> findByStatus(OrderStatus status);
}