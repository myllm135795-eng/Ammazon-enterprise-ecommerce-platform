package com.ammazon.search.controller;

import com.ammazon.search.service.SearchService;
import com.ammazon.shared.dto.ApiResponse;
import com.ammazon.shared.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Search products by name.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SearchResultDto>> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Search endpoint called with query: {}", query);
        Pageable pageable = PageRequest.of(page, size);
        SearchResultDto results = searchService.searchByName(query, pageable);
        return ResponseEntity.ok(ApiResponse.ok(results));
    }

    /**
     * Search products by category.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<SearchResultDto>> searchByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("Search by category endpoint called: {}", category);
        Pageable pageable = PageRequest.of(page, size);
        SearchResultDto results = searchService.searchByCategory(category, pageable);
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