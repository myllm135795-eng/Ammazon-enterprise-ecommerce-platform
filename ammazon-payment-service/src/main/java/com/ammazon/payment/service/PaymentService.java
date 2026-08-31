package com.ammazon.payment.service;

import com.ammazon.commons.enums.PaymentStatus;
import com.ammazon.payment.entity.Payment;
import com.ammazon.payment.repository.PaymentRepository;
import com.ammazon.shared.dto.PaymentDto;
import com.ammazon.shared.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Payment service with idempotent operations.
 */
@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Process payment idempotently using idempotency key.
     */
    @Transactional
    public PaymentDto processPayment(String orderId, String userId, BigDecimal amount,
                                     String currency, String paymentMethod, String idempotencyKey) {
        log.info("Processing payment for orderId: {}, idempotencyKey: {}", orderId, idempotencyKey);

        // Check if payment already processed (idempotency)
        Optional<Payment> existingPayment = paymentRepository.findByIdempotencyKey(idempotencyKey);
        if (existingPayment.isPresent()) {
            log.info("Payment already processed with idempotency key: {}", idempotencyKey);
            return mapToDto(existingPayment.get());
        }

        Payment payment = Payment.builder()
                .orderId(orderId)
                .userId(userId)
                .amount(amount)
                .currency(currency)
                .paymentMethod(paymentMethod)
                .status(PaymentStatus.PROCESSING)
                .idempotencyKey(idempotencyKey)
                .build();

        try {
            // Call payment gateway (Stripe, PayPal, etc.)
            String transactionId = callPaymentGateway(payment);
            payment.setTransactionId(transactionId);
            payment.setStatus(PaymentStatus.COMPLETED);
            
            log.info("Payment completed with transactionId: {}", transactionId);
        } catch (Exception e) {
            log.error("Payment processing failed", e);
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason(e.getMessage());
        }

        Payment saved = paymentRepository.save(payment);
        return mapToDto(saved);
    }

    /**
     * Refund payment.
     */
    @Transactional
    public PaymentDto refundPayment(String paymentId, String idempotencyKey) {
        log.info("Refunding payment: {}, idempotencyKey: {}", paymentId, idempotencyKey);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ValidationException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new ValidationException("Only completed payments can be refunded");
        }

        try {
            // Call payment gateway to refund
            refundPaymentGateway(payment);
            payment.setStatus(PaymentStatus.REFUNDED);
            log.info("Payment refunded successfully");
        } catch (Exception e) {
            log.error("Refund failed", e);
            throw new ValidationException("Refund failed: " + e.getMessage());
        }

        Payment refunded = paymentRepository.save(payment);
        return mapToDto(refunded);
    }

    /**
     * Call payment gateway (placeholder).
     */
    private String callPaymentGateway(Payment payment) {
        // In real scenario, call Stripe, PayPal, etc.
        log.info("Calling payment gateway for amount: {}", payment.getAmount());
        // Simulate transaction ID
        return "TXN-" + System.currentTimeMillis();
    }

    /**
     * Refund from payment gateway (placeholder).
     */
    private void refundPaymentGateway(Payment payment) {
        log.info("Refunding transaction: {}", payment.getTransactionId());
        // In real scenario, call payment gateway to refund
    }

    /**
     * Map Payment entity to DTO.
     */
    private PaymentDto mapToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .userId(payment.getUserId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .failureReason(payment.getFailureReason())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}