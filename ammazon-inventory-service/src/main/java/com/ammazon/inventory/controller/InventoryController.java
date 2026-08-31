package com.ammazon.inventory.controller;

import com.ammazon.inventory.service.InventoryService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Inventory API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * Check availability.
     */
    @GetMapping("/{productId}/available")
    public ResponseEntity<ApiResponse<Integer>> checkAvailability(@PathVariable String productId) {
        log.info("Check availability endpoint called for productId: {}", productId);
        int available = inventoryService.getAvailableQuantity(productId);
        return ResponseEntity.ok(ApiResponse.ok(available));
    }

    /**
     * Reserve inventory.
     */
    @PostMapping("/{productId}/reserve")
    public ResponseEntity<ApiResponse<String>> reserve(@PathVariable String productId,
                                                       @RequestParam int quantity) {
        log.info("Reserve inventory endpoint called for productId: {}, quantity: {}", productId, quantity);
        inventoryService.reserveInventory(productId, quantity);
        return ResponseEntity.ok(ApiResponse.ok("Inventory reserved"));
    }

    /**
     * Release inventory.
     */
    @PostMapping("/{productId}/release")
    public ResponseEntity<ApiResponse<String>> release(@PathVariable String productId,
                                                       @RequestParam int quantity) {
        log.info("Release inventory endpoint called for productId: {}, quantity: {}", productId, quantity);
        inventoryService.releaseInventory(productId, quantity);
        return ResponseEntity.ok(ApiResponse.ok("Inventory released"));
    }

    /**
     * Confirm inventory.
     */
    @PostMapping("/{productId}/confirm")
    public ResponseEntity<ApiResponse<String>> confirm(@PathVariable String productId,
                                                       @RequestParam int quantity) {
        log.info("Confirm inventory endpoint called for productId: {}, quantity: {}", productId, quantity);
        inventoryService.confirmInventory(productId, quantity);
        return ResponseEntity.ok(ApiResponse.ok("Inventory confirmed"));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Inventory Service is running");
    }
}