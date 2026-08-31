package com.ammazon.search.service;

import com.ammazon.search.document.ProductSearchDocument;
import com.ammazon.search.repository.ProductSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Search service with Elasticsearch integration.
 */
@Slf4j
@Service
public class SearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    /**
     * Search products by name or description.
     */
    public List<ProductSearchDocument> search(String query) {
        log.info("Searching products with query: {}", query);
        return productSearchRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
    }

    /**
     * Search products by category.
     */
    public List<ProductSearchDocument> searchByCategory(String category) {
        log.info("Searching products by category: {}", category);
        return productSearchRepository.findByCategory(category);
    }

    /**
     * Index product in Elasticsearch.
     */
    public void indexProduct(ProductSearchDocument document) {
        log.info("Indexing product: {}", document.getId());
        productSearchRepository.save(document);
    }

    /**
     * Listen to product events and update Elasticsearch index.
     */
    @KafkaListener(topics = "product-events", groupId = "search-service")
    public void handleProductEvent(String event) {
        log.info("Processing product event: {}", event);
        // Parse event and update Elasticsearch index
    }

    /**
     * Delete product from index.
     */
    public void deleteProduct(String productId) {
        log.info("Deleting product from index: {}", productId);
        productSearchRepository.deleteById(productId);
    }
}