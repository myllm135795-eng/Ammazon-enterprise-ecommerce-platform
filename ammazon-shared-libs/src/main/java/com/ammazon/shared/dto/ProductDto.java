package com.ammazon.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Product DTO for API responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}