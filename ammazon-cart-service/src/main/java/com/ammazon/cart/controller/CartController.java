package com.ammazon.cart.controller;

import com.ammazon.cart.model.Cart;
import com.ammazon.cart.model.CartItem;
import com.ammazon.cart.service.CartService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Reactive cart API controller.
 * All endpoints return Mono/Flux for reactive processing.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * Get cart for user.
     */
    @GetMapping("/{userId}")
    public Mono<ResponseEntity<ApiResponse<Cart>>> getCart(@PathVariable String userId) {
        log.info("Get cart endpoint called for userId: {}", userId);
        return cartService.getCart(userId)
                .map(cart -> ResponseEntity.ok(ApiResponse.ok(cart)))
                .doOnError(e -> log.error("Error getting cart", e));
    }

    /**
     * Add item to cart.
     */
    @PostMapping("/{userId}/items")
    public Mono<ResponseEntity<ApiResponse<Cart>>> addToCart(@PathVariable String userId,
                                                             @RequestBody CartItem item) {
        log.info("Add to cart endpoint called for userId: {}", userId);
        return cartService.addToCart(userId, item)
                .map(cart -> ResponseEntity.ok(ApiResponse.ok(cart, "Item added to cart")))
                .doOnError(e -> log.error("Error adding to cart", e));
    }

    /**
     * Remove item from cart.
     */
    @DeleteMapping("/{userId}/items/{productId}")
    public Mono<ResponseEntity<ApiResponse<Cart>>> removeFromCart(@PathVariable String userId,
                                                                  @PathVariable String productId) {
        log.info("Remove from cart endpoint called for userId: {}, productId: {}", userId, productId);
        return cartService.removeFromCart(userId, productId)
                .map(cart -> ResponseEntity.ok(ApiResponse.ok(cart, "Item removed from cart")))
                .doOnError(e -> log.error("Error removing from cart", e));
    }

    /**
     * Clear cart.
     */
    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<ApiResponse<String>>> clearCart(@PathVariable String userId) {
        log.info("Clear cart endpoint called for userId: {}", userId);
        return cartService.clearCart(userId)
                .then(Mono.just(ResponseEntity.ok(ApiResponse.ok("Cart cleared", "Cart cleared successfully"))))
                .doOnError(e -> log.error("Error clearing cart", e));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public Mono<ResponseEntity<String>> health() {
        return Mono.just(ResponseEntity.ok("Cart Service is running"));
    }
}