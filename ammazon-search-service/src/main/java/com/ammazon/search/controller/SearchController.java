package com.ammazon.search.controller;

import com.ammazon.search.document.ProductSearchDocument;
import com.ammazon.search.service.SearchService;
import com.ammazon.shared.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Search API controller.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * Search products by query.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductSearchDocument>>> search(@RequestParam String q) {
        log.info("Search endpoint called with query: {}", q);
        List<ProductSearchDocument> results = searchService.searchByName(q);
        return ResponseEntity.ok(ApiResponse.ok(results));
    }

    /**
     * Search products by category.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductSearchDocument>>> searchByCategory(@PathVariable String category) {
        log.info("Search by category endpoint called: {}", category);
        List<ProductSearchDocument> results = searchService.searchByCategory(category);
        return ResponseEntity.ok(ApiResponse.ok(results));
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Search Service is running");
    }
}