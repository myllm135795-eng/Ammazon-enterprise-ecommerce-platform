package com.ammazon.analytics.controller;

import com.ammazon.analytics.service.AnalyticsService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
     * Get dashboard metrics.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboard() {
        log.info("Dashboard endpoint called");
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("totalOrders", analyticsService.getTotalOrders());
        metrics.put("totalRevenue", analyticsService.getTotalRevenue());
        return ResponseEntity.ok(ApiResponse.ok(metrics));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Analytics Service is running");
    }
}