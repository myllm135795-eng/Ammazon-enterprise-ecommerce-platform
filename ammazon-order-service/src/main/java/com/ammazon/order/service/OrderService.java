package com.ammazon.order.service;

import com.ammazon.commons.enums.OrderStatus;
import com.ammazon.order.entity.Order;
import com.ammazon.order.entity.OrderItem;
import com.ammazon.order.repository.OrderRepository;
import com.ammazon.shared.dto.OrderDto;
import com.ammazon.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Order service with Saga pattern orchestration.
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Create order (orchestration saga starts here).
     */
    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating order for userId: {}", order.getUserId());
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);
        // Publish OrderCreatedEvent to Kafka
        // This triggers the saga orchestration
        log.info("Order created with id: {}", savedOrder.getId());
        return savedOrder;
    }

    /**
     * Get order by ID.
     */
    public OrderDto getOrderById(String orderId) {
        log.info("Getting order with ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        return mapToDto(order);
    }

    /**
     * Get orders by user ID.
     */
    public List<OrderDto> getOrdersByUserId(String userId) {
        log.info("Getting orders for userId: {}", userId);
        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update order status.
     */
    @Transactional
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        log.info("Updating order status for orderId: {}, status: {}", orderId, status);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    /**
     * Cancel order.
     */
    @Transactional
    public Order cancelOrder(String orderId) {
        log.info("Cancelling order: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    /**
     * Map Order entity to DTO.
     */
    private OrderDto mapToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .shippingCost(order.getShippingCost())
                .tax(order.getTax())
                .shippingAddress(order.getShippingAddress())
                .trackingNumber(order.getTrackingNumber())
                .items(order.getItems() != null ? order.getItems().stream()
                        .map(item -> OrderDto.OrderItemDto.builder()
                                .productId(item.getProductId())
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .unitPrice(item.getUnitPrice())
                                .totalPrice(item.getTotalPrice())
                                .build())
                        .collect(Collectors.toList()) : null)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}