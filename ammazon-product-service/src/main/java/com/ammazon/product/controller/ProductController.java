package com.ammazon.product.controller;

import com.ammazon.product.service.ProductService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Get product by ID.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable String productId) {
        log.info("Get product endpoint called for ID: {}", productId);
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(ApiResponse.ok(product));
    }

    /**
     * Get all active products.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {
        log.info("Get all products endpoint called");
        List<ProductDto> products = productService.getAllActiveProducts();
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /**
     * Get products by category.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCategory(@PathVariable String category) {
        log.info("Get products by category endpoint called: {}", category);
        List<ProductDto> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /**
     * Search products.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> searchProducts(@RequestParam String query) {
        log.info("Search products endpoint called with query: {}", query);
        List<ProductDto> products = productService.searchProducts(query);
        return ResponseEntity.ok(ApiResponse.ok(products));
    }

    /**
     * Create product.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto productDto) {
        log.info("Create product endpoint called");
        ProductDto created = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(created));
    }

    /**
     * Update product.
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable String productId,
                                                                 @RequestBody ProductDto productDto) {
        log.info("Update product endpoint called for ID: {}", productId);
        ProductDto updated = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok(ApiResponse.ok(updated, "Product updated successfully"));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Product Service is running");
    }
}