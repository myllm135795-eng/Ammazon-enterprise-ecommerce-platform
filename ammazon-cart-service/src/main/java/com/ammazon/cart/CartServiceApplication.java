package com.ammazon.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Cart Service Application.
 * Reactive shopping cart service using Spring WebFlux and Redis.
 * Demonstrates Project Reactor patterns and virtual threads.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}