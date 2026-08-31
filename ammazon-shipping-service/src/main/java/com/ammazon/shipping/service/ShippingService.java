package com.ammazon.shipping.service;

import com.ammazon.commons.enums.ShippingStatus;
import com.ammazon.shipping.entity.Shipment;
import com.ammazon.shipping.repository.ShipmentRepository;
import com.ammazon.shared.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Shipping service for managing shipments.
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
    public Shipment createShipment(String orderId, String carrier, String shippingAddress) {
        log.info("Creating shipment for orderId: {}, carrier: {}", orderId, carrier);
        
        Shipment shipment = Shipment.builder()
                .orderId(orderId)
                .carrier(carrier)
                .shippingAddress(shippingAddress)
                .status(ShippingStatus.PENDING)
                .build();

        // Generate tracking number from carrier
        String trackingNumber = generateTrackingNumber(carrier);
        shipment.setTrackingNumber(trackingNumber);

        return shipmentRepository.save(shipment);
    }

    /**
     * Get shipment by order ID.
     */
    public Shipment getShipmentByOrderId(String orderId) {
        log.info("Getting shipment for orderId: {}", orderId);
        return shipmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ValidationException("Shipment not found"));
    }

    /**
     * Track shipment.
     */
    public Shipment trackShipment(String trackingNumber) {
        log.info("Tracking shipment: {}", trackingNumber);
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new ValidationException("Shipment not found"));
    }

    /**
     * Update shipment status.
     */
    @Transactional
    public Shipment updateShipmentStatus(String shipmentId, ShippingStatus status) {
        log.info("Updating shipment status: {}, status: {}", shipmentId, status);
        
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new ValidationException("Shipment not found"));
        
        shipment.setStatus(status);
        
        if (status == ShippingStatus.DELIVERED) {
            shipment.setActualDelivery(String.valueOf(System.currentTimeMillis()));
        }

        return shipmentRepository.save(shipment);
    }

    /**
     * Generate tracking number (placeholder).
     */
    private String generateTrackingNumber(String carrier) {
        return carrier.toUpperCase() + "-" + System.currentTimeMillis();
    }
}