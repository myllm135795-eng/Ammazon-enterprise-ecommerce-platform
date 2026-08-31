package com.ammazon.commons.constants;

/**
 * Kafka topic constants.
 * Centralized definition of all Kafka topics used across services.
 */
public final class KafkaTopics {
    private KafkaTopics() {
    }

    // User Events
    public static final String USER_REGISTERED = "user-registered";
    public static final String USER_PROFILE_UPDATED = "user-profile-updated";
    public static final String USER_DELETED = "user-deleted";

    // Product Events
    public static final String PRODUCT_CREATED = "product-created";
    public static final String PRODUCT_UPDATED = "product-updated";
    public static final String PRODUCT_DELETED = "product-deleted";
    public static final String PRODUCT_PRICE_CHANGED = "product-price-changed";

    // Inventory Events
    public static final String INVENTORY_RESERVED = "inventory-reserved";
    public static final String INVENTORY_RELEASED = "inventory-released";
    public static final String STOCK_LOW = "stock-low";
    public static final String STOCK_OUT = "stock-out";

    // Order Events
    public static final String ORDER_CREATED = "order-created";
    public static final String ORDER_CONFIRMED = "order-confirmed";
    public static final String ORDER_PAYMENT_COMPLETED = "order-payment-completed";
    public static final String ORDER_PROCESSING = "order-processing";
    public static final String ORDER_SHIPPED = "order-shipped";
    public static final String ORDER_DELIVERED = "order-delivered";
    public static final String ORDER_CANCELLED = "order-cancelled";
    public static final String ORDER_REFUNDED = "order-refunded";

    // Payment Events
    public static final String PAYMENT_INITIATED = "payment-initiated";
    public static final String PAYMENT_COMPLETED = "payment-completed";
    public static final String PAYMENT_FAILED = "payment-failed";
    public static final String PAYMENT_REFUND_INITIATED = "payment-refund-initiated";
    public static final String PAYMENT_REFUND_COMPLETED = "payment-refund-completed";

    // Notification Events
    public static final String EMAIL_NOTIFICATION = "email-notification";
    public static final String SMS_NOTIFICATION = "sms-notification";
    public static final String PUSH_NOTIFICATION = "push-notification";

    // Shipping Events
    public static final String SHIPMENT_CREATED = "shipment-created";
    public static final String SHIPMENT_PICKED = "shipment-picked";
    public static final String SHIPMENT_SHIPPED = "shipment-shipped";
    public static final String SHIPMENT_DELIVERED = "shipment-delivered";
    public static final String SHIPMENT_FAILED = "shipment-failed";

    // Analytics Events
    public static final String USER_ACTIVITY = "user-activity";
    public static final String PURCHASE_EVENT = "purchase-event";
    public static final String SEARCH_EVENT = "search-event";
    public static final String CART_EVENT = "cart-event";
}