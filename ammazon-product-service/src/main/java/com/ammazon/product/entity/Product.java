package com.ammazon.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product entity stored in MongoDB.
 */
@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private int stockQuantity;
    private double rating;
    private int reviewCount;
    private boolean active;
    private String vendorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
