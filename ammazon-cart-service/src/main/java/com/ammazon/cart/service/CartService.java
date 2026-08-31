package com.ammazon.cart.service;

import com.ammazon.cart.model.Cart;
import com.ammazon.cart.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Reactive cart service using Spring WebFlux and Redis.
 * Demonstrates reactive programming with Project Reactor.
 */
@Slf4j
@Service
public class CartService {
    private static final String CART_KEY_PREFIX = "cart:";
    private static final Duration CART_EXPIRY = Duration.ofDays(7);

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Get cart for user.
     */
    public Mono<Cart> getCart(String userId) {
        log.info("Getting cart for userId: {}", userId);
        return reactiveRedisTemplate.opsForValue()
                .get(getCartKey(userId))
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, Cart.class);
                    } catch (Exception e) {
                        log.error("Error deserializing cart", e);
                        throw new RuntimeException(e);
                    }
                })
                .switchIfEmpty(Mono.just(Cart.builder()
                        .userId(userId)
                        .items(new java.util.ArrayList<>())
                        .totalPrice(java.math.BigDecimal.ZERO)
                        .createdAt(System.currentTimeMillis())
                        .updatedAt(System.currentTimeMillis())
                        .build()));
    }

    /**
     * Add item to cart.
     */
    public Mono<Cart> addToCart(String userId, CartItem item) {
        log.info("Adding item to cart for userId: {}, productId: {}", userId, item.getProductId());
        return getCart(userId)
                .doOnNext(cart -> cart.addItem(item))
                .flatMap(cart -> saveCart(userId, cart))
                .doOnNext(cart -> log.info("Item added to cart, total: {}", cart.getTotalPrice()))
                .doOnError(e -> log.error("Error adding item to cart", e));
    }

    /**
     * Remove item from cart.
     */
    public Mono<Cart> removeFromCart(String userId, String productId) {
        log.info("Removing item from cart for userId: {}, productId: {}", userId, productId);
        return getCart(userId)
                .doOnNext(cart -> cart.removeItem(productId))
                .flatMap(cart -> saveCart(userId, cart))
                .doOnError(e -> log.error("Error removing item from cart", e));
    }

    /**
     * Clear cart.
     */
    public Mono<Void> clearCart(String userId) {
        log.info("Clearing cart for userId: {}", userId);
        return reactiveRedisTemplate.delete(getCartKey(userId))
                .then()
                .doOnError(e -> log.error("Error clearing cart", e));
    }

    /**
     * Save cart to Redis.
     */
    private Mono<Cart> saveCart(String userId, Cart cart) {
        cart.setUpdatedAt(System.currentTimeMillis());
        try {
            String json = objectMapper.writeValueAsString(cart);
            return reactiveRedisTemplate.opsForValue()
                    .set(getCartKey(userId), json, CART_EXPIRY)
                    .then(Mono.just(cart));
        } catch (Exception e) {
            log.error("Error serializing cart", e);
            return Mono.error(e);
        }
    }

    /**
     * Get all carts for users (for analytics).
     */
    public Flux<Cart> getAllCarts() {
        return reactiveRedisTemplate.keys(CART_KEY_PREFIX + "*")
                .flatMap(key -> reactiveRedisTemplate.opsForValue().get(key))
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, Cart.class);
                    } catch (Exception e) {
                        log.error("Error deserializing cart", e);
                        return null;
                    }
                })
                .filter(cart -> cart != null);
    }

    private String getCartKey(String userId) {
        return CART_KEY_PREFIX + userId;
    }
}