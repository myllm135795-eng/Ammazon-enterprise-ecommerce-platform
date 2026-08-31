package com.ammazon.commons.events;

import java.math.BigDecimal;
import java.util.List;

/**
 * Event published when an order is created.
 */
public class OrderCreatedEvent extends DomainEvent {
    private String orderId;
    private String userId;
    private List<OrderItemDto> items;
    private BigDecimal totalAmount;
    private String shippingAddress;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String userId, List<OrderItemDto> items,
                             BigDecimal totalAmount, String shippingAddress) {
        this.orderId = orderId;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String getEventType() {
        return "OrderCreatedEvent";
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public static class OrderItemDto {
        private String productId;
        private int quantity;
        private BigDecimal price;

        public OrderItemDto() {
        }

        public OrderItemDto(String productId, int quantity, BigDecimal price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        // Getters and Setters
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}