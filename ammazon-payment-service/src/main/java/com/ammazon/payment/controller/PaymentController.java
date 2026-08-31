package com.ammazon.payment.controller;

import com.ammazon.payment.service.PaymentService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.PaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Payment API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Process payment with idempotency key.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDto>> processPayment(
            @RequestParam String orderId,
            @RequestParam String userId,
            @RequestParam BigDecimal amount,
            @RequestParam String currency,
            @RequestParam String paymentMethod,
            @RequestHeader("X-Idempotency-Key") String idempotencyKey) {
        log.info("Process payment endpoint called for orderId: {}", orderId);
        PaymentDto payment = paymentService.processPayment(orderId, userId, amount, currency, paymentMethod, idempotencyKey);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(payment));
    }

    /**
     * Refund payment with idempotency key.
     */
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<ApiResponse<PaymentDto>> refundPayment(
            @PathVariable String paymentId,
            @RequestHeader("X-Idempotency-Key") String idempotencyKey) {
        log.info("Refund payment endpoint called for paymentId: {}", paymentId);
        PaymentDto refunded = paymentService.refundPayment(paymentId, idempotencyKey);
        return ResponseEntity.ok(ApiResponse.ok(refunded, "Payment refunded"));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is running");
    }
}