package com.ammazon.search.service;

import com.ammazon.search.document.ProductSearchDocument;
import com.ammazon.search.repository.ProductSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Search service for full-text search operations.
 */
@Slf4j
@Service
public class SearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    /**
     * Search products by name.
     */
    public List<ProductSearchDocument> searchByName(String query) {
        log.info("Searching products by name: {}", query);
        return productSearchRepository.findByNameContainingIgnoreCase(query);
    }

    /**
     * Search products by category.
     */
    public List<ProductSearchDocument> searchByCategory(String category) {
        log.info("Searching products by category: {}", category);
        return productSearchRepository.findByCategory(category);
    }

    /**
     * Get all active products for search.
     */
    public List<ProductSearchDocument> getActiveProducts() {
        log.info("Getting all active products for search");
        return productSearchRepository.findByActiveTrue();
    }

    /**
     * Index product document.
     */
    public ProductSearchDocument indexProduct(ProductSearchDocument document) {
        log.info("Indexing product: {}", document.getId());
        return productSearchRepository.save(document);
    }

    /**
     * Listen to product events and index them.
     */
    @KafkaListener(topics = "product-indexed", groupId = "search-service")
    public void handleProductIndexing(ProductSearchDocument document) {
        log.info("Received product indexing event: {}", document.getId());
        indexProduct(document);
    }
}