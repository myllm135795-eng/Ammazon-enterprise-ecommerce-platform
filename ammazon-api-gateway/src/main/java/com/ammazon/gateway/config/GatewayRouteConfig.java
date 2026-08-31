package com.ammazon.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

/**
 * Gateway routing configuration.
 * Defines routes to backend microservices.
 */
@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Auth Service
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("lb://ammazon-auth-service"))
                // User Service
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .uri("lb://ammazon-user-service"))
                // Product Service
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .uri("lb://ammazon-product-service"))
                // Inventory Service
                .route("inventory-service", r -> r
                        .path("/api/v1/inventory/**")
                        .uri("lb://ammazon-inventory-service"))
                // Cart Service
                .route("cart-service", r -> r
                        .path("/api/v1/cart/**")
                        .uri("lb://ammazon-cart-service"))
                // Order Service
                .route("order-service", r -> r
                        .path("/api/v1/orders/**")
                        .uri("lb://ammazon-order-service"))
                // Payment Service
                .route("payment-service", r -> r
                        .path("/api/v1/payments/**")
                        .uri("lb://ammazon-payment-service"))
                // Search Service
                .route("search-service", r -> r
                        .path("/api/v1/search/**")
                        .uri("lb://ammazon-search-service"))
                .build();
    }
}