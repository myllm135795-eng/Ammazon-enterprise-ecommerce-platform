package com.ammazon.shipping.controller;

import com.ammazon.shipping.entity.Shipment;
import com.ammazon.shipping.service.ShippingService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Shipping API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/shipping")
@CrossOrigin(origins = "*")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    /**
     * Create shipment.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Shipment>> createShipment(
            @RequestParam String orderId,
            @RequestParam String carrier,
            @RequestParam String shippingAddress) {
        log.info("Create shipment endpoint called for orderId: {}", orderId);
        Shipment shipment = shippingService.createShipment(orderId, carrier, shippingAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(shipment));
    }

    /**
     * Track shipment.
     */
    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ApiResponse<Shipment>> trackShipment(@PathVariable String trackingNumber) {
        log.info("Track shipment endpoint called for trackingNumber: {}", trackingNumber);
        Shipment shipment = shippingService.trackShipment(trackingNumber);
        return ResponseEntity.ok(ApiResponse.ok(shipment));
    }

    /**
     * Get shipment by order ID.
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<Shipment>> getShipmentByOrderId(@PathVariable String orderId) {
        log.info("Get shipment endpoint called for orderId: {}", orderId);
        Shipment shipment = shippingService.getShipmentByOrderId(orderId);
        return ResponseEntity.ok(ApiResponse.ok(shipment));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Shipping Service is running");
    }
}