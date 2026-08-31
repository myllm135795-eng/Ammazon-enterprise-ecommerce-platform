package com.ammazon.analytics.controller;

import com.ammazon.analytics.service.AnalyticsService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Analytics API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    /**
     * Get sales metrics.
     */
    @GetMapping("/sales-metrics")
    public ResponseEntity<ApiResponse<String>> getSalesMetrics() {
        log.info("Get sales metrics endpoint called");
        String metrics = analyticsService.getSalesMetrics();
        return ResponseEntity.ok(ApiResponse.ok(metrics));
    }

    /**
     * Get trending products.
     */
    @GetMapping("/trending-products")
    public ResponseEntity<ApiResponse<String>> getTrendingProducts() {
        log.info("Get trending products endpoint called");
        String trending = analyticsService.getTrendingProducts();
        return ResponseEntity.ok(ApiResponse.ok(trending));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Analytics Service is running");
    }
}