package com.ammazon.inventory.service;

import com.ammazon.inventory.entity.Inventory;
import com.ammazon.inventory.repository.InventoryRepository;
import com.ammazon.shared.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Inventory service for managing stock.
 */
@Slf4j
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Reserve inventory for an order.
     */
    @Transactional
    public void reserveInventory(String productId, int quantity) {
        log.info("Reserving inventory for productId: {}, quantity: {}", productId, quantity);
        
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ValidationException("Product not found in inventory"));

        int availableQuantity = inventory.getQuantity() - inventory.getReserved();
        if (availableQuantity < quantity) {
            throw new ValidationException("Insufficient inventory. Available: " + availableQuantity);
        }

        inventory.setReserved(inventory.getReserved() + quantity);
        inventoryRepository.save(inventory);
    }

    /**
     * Release reserved inventory.
     */
    @Transactional
    public void releaseInventory(String productId, int quantity) {
        log.info("Releasing inventory for productId: {}, quantity: {}", productId, quantity);
        
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ValidationException("Product not found in inventory"));

        inventory.setReserved(Math.max(0, inventory.getReserved() - quantity));
        inventoryRepository.save(inventory);
    }

    /**
     * Confirm inventory deduction (after successful payment).
     */
    @Transactional
    public void confirmInventory(String productId, int quantity) {
        log.info("Confirming inventory for productId: {}, quantity: {}", productId, quantity);
        
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ValidationException("Product not found in inventory"));

        if (inventory.getReserved() < quantity) {
            throw new ValidationException("Cannot confirm more than reserved quantity");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventory.setReserved(inventory.getReserved() - quantity);
        inventoryRepository.save(inventory);
    }

    /**
     * Get available quantity.
     */
    public int getAvailableQuantity(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ValidationException("Product not found in inventory"));
        return inventory.getQuantity() - inventory.getReserved();
    }
}