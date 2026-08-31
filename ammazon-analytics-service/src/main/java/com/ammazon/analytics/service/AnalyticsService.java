package com.ammazon.analytics.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Analytics service for processing events and generating metrics.
 */
@Slf4j
@Service
public class AnalyticsService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Process order events for analytics.
     */
    @KafkaListener(topics = "order-events", groupId = "analytics-service")
    public void handleOrderEvents(String event) {
        log.info("Processing order event for analytics: {}", event);
        // Parse event and update metrics in Redis
        updateOrderMetrics(event);
    }

    /**
     * Process payment events for analytics.
     */
    @KafkaListener(topics = "payment-events", groupId = "analytics-service")
    public void handlePaymentEvents(String event) {
        log.info("Processing payment event for analytics: {}", event);
        // Parse event and update metrics in Redis
        updatePaymentMetrics(event);
    }

    /**
     * Update order metrics.
     */
    private void updateOrderMetrics(String event) {
        // Increment order counters
        redisTemplate.opsForValue().increment("metrics:orders:total");
        log.debug("Order metrics updated");
    }

    /**
     * Update payment metrics.
     */
    private void updatePaymentMetrics(String event) {
        // Increment payment counters
        redisTemplate.opsForValue().increment("metrics:payments:total");
        log.debug("Payment metrics updated");
    }

    /**
     * Get order metrics.
     */
    public Map<String, Object> getOrderMetrics() {
        log.info("Retrieving order metrics");
        return Map.of(
                "total_orders", redisTemplate.opsForValue().get("metrics:orders:total"),
                "timestamp", System.currentTimeMillis()
        );
    }
}