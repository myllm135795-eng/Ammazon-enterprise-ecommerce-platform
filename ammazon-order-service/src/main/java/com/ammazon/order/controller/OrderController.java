package com.ammazon.order.controller;

import com.ammazon.commons.enums.OrderStatus;
import com.ammazon.order.entity.Order;
import com.ammazon.order.service.OrderService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Order API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Create order.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Order>> createOrder(@RequestBody Order order) {
        log.info("Create order endpoint called for userId: {}", order.getUserId());
        Order created = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(created));
    }

    /**
     * Get order by ID.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(@PathVariable String orderId) {
        log.info("Get order endpoint called for orderId: {}", orderId);
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.ok(order));
    }

    /**
     * Get orders by user ID.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderDto>>> getUserOrders(@PathVariable String userId) {
        log.info("Get user orders endpoint called for userId: {}", userId);
        List<OrderDto> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(ApiResponse.ok(orders));
    }

    /**
     * Cancel order.
     */
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Order>> cancelOrder(@PathVariable String orderId) {
        log.info("Cancel order endpoint called for orderId: {}", orderId);
        Order cancelled = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.ok(cancelled, "Order cancelled"));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service is running");
    }
}