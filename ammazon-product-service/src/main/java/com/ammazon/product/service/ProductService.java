package com.ammazon.product.service;

import com.ammazon.product.entity.Product;
import com.ammazon.product.repository.ProductRepository;
import com.ammazon.shared.dto.ProductDto;
import com.ammazon.shared.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Product service for managing product catalog.
 */
@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get product by ID with caching.
     */
    @Cacheable(value = "products", key = "#productId")
    public ProductDto getProductById(String productId) {
        log.info("Getting product with ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));
        return mapToDto(product);
    }

    /**
     * Get all active products.
     */
    public List<ProductDto> getAllActiveProducts() {
        log.info("Getting all active products");
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search products by category.
     */
    public List<ProductDto> getProductsByCategory(String category) {
        log.info("Getting products by category: {}", category);
        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Search products by name.
     */
    public List<ProductDto> searchProducts(String name) {
        log.info("Searching products by name: {}", name);
        return productRepository.searchByName(name)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Create a new product.
     */
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Creating new product: {}", productDto.getName());
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .imageUrl(productDto.getImageUrl())
                .stockQuantity(productDto.getStockQuantity())
                .rating(0.0)
                .reviewCount(0)
                .active(true)
                .build();
        
        Product saved = productRepository.save(product);
        return mapToDto(saved);
    }

    /**
     * Update product.
     */
    @CacheEvict(value = "products", key = "#productId")
    public ProductDto updateProduct(String productId, ProductDto productDto) {
        log.info("Updating product: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setActive(productDto.isActive());

        Product updated = productRepository.save(product);
        return mapToDto(updated);
    }

    /**
     * Map Product entity to DTO.
     */
    private ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockQuantity(product.getStockQuantity())
                .rating(product.getRating())
                .reviewCount(product.getReviewCount())
                .active(product.isActive())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}