package com.ammazon.shipping.service;

import com.ammazon.shipping.entity.Shipment;
import com.ammazon.shipping.repository.ShipmentRepository;
import com.ammazon.shared.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Shipping service for handling shipments and tracking.
 */
@Slf4j
@Service
public class ShippingService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    /**
     * Create shipment.
     */
    @Transactional
    public Shipment createShipment(String orderId, String shippingAddress) {
        log.info("Creating shipment for orderId: {}", orderId);
        
        Shipment shipment = Shipment.builder()
                .orderId(orderId)
                .shippingAddress(shippingAddress)
                .trackingNumber(generateTrackingNumber())
                .carrier("Standard Shipping")
                .build();

        return shipmentRepository.save(shipment);
    }

    /**
     * Get shipment by order ID.
     */
    public Shipment getShipmentByOrderId(String orderId) {
        log.info("Getting shipment for orderId: {}", orderId);
        return shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ValidationException("Shipment not found for order: " + orderId));
    }

    /**
     * Track shipment.
     */
    public Shipment trackShipment(String trackingNumber) {
        log.info("Tracking shipment: {}", trackingNumber);
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ValidationException("Tracking number not found: " + trackingNumber));
    }

    /**
     * Update shipment status.
     */
    @Transactional
    public Shipment updateShipmentStatus(String shipmentId, String status) {
        log.info("Updating shipment status for shipmentId: {}, status: {}", shipmentId, status);
        
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ValidationException("Shipment not found"));

        shipment.setStatus(status);
        
        if ("SHIPPED".equals(status)) {
            shipment.setShipDate(LocalDateTime.now());
            shipment.setEstimatedDelivery(LocalDateTime.now().plusDays(5)); // Placeholder
        } else if ("DELIVERED".equals(status)) {
            shipment.setDeliveryDate(LocalDateTime.now());
        }

        return shipmentRepository.save(shipment);
    }

    /**
     * Generate tracking number.
     */
    private String generateTrackingNumber() {
        return "AMZN-" + System.currentTimeMillis();
    }
}