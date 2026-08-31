package com.ammazon.analytics.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Analytics service for tracking metrics and events.
 */
@Slf4j
@Service
public class AnalyticsService {

    @Autowired
    private MeterRegistry meterRegistry;

    private final AtomicLong totalOrders = new AtomicLong(0);
    private final AtomicLong totalRevenue = new AtomicLong(0);

    /**
     * Listen to order events and track analytics.
     */
    @KafkaListener(topics = "order-events", groupId = "analytics-service")
    public void handleOrderEvent(String event) {
        log.info("Processing order event: {}", event);
        trackOrderMetric();
    }

    /**
     * Track order metric.
     */
    public void trackOrderMetric() {
        Counter.builder("orders.created")
                .description("Total orders created")
                .register(meterRegistry)
                .increment();
        totalOrders.incrementAndGet();
    }

    /**
     * Track payment metric.
     */
    public void trackPaymentMetric(long amount) {
        Counter.builder("payments.processed")
                .description("Total payments processed")
                .register(meterRegistry)
                .increment();
        totalRevenue.addAndGet(amount);
    }

    /**
     * Listen to payment events.
     */
    @KafkaListener(topics = "payment-events", groupId = "analytics-service")
    public void handlePaymentEvent(String event) {
        log.info("Processing payment event: {}", event);
        trackPaymentMetric(1);
    }

    /**
     * Get total orders.
     */
    public long getTotalOrders() {
        return totalOrders.get();
    }

    /**
     * Get total revenue.
     */
    public long getTotalRevenue() {
        return totalRevenue.get();
    }
}