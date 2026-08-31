package com.ammazon.search.service;

import com.ammazon.search.document.ProductDocument;
import com.ammazon.search.repository.ProductSearchRepository;
import com.ammazon.shared.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Search service for Elasticsearch operations.
 */
@Slf4j
@Service
public class SearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    /**
     * Search products by name.
     */
    public SearchResultDto searchByName(String query, Pageable pageable) {
        log.info("Searching products by name: {}", query);
        Page<ProductDocument> results = productSearchRepository.findByNameContainingIgnoreCase(query, pageable);
        return buildSearchResult(results);
    }

    /**
     * Search products by category.
     */
    public SearchResultDto searchByCategory(String category, Pageable pageable) {
        log.info("Searching products by category: {}", category);
        Page<ProductDocument> results = productSearchRepository.findByCategory(category, pageable);
        return buildSearchResult(results);
    }

    /**
     * Get all active products.
     */
    public SearchResultDto getActiveProducts(Pageable pageable) {
        log.info("Getting all active products");
        Page<ProductDocument> results = productSearchRepository.findByActiveTrue(pageable);
        return buildSearchResult(results);
    }

    /**
     * Index product document.
     */
    public ProductDocument indexProduct(ProductDocument document) {
        log.info("Indexing product: {}", document.getId());
        return productSearchRepository.save(document);
    }

    /**
     * Delete product from index.
     */
    public void deleteProductIndex(String productId) {
        log.info("Deleting product from index: {}", productId);
        productSearchRepository.deleteById(productId);
    }

    /**
     * Build search result from Page.
     */
    private SearchResultDto buildSearchResult(Page<ProductDocument> page) {
        return SearchResultDto.builder()
                .content(page.getContent().stream()
                        .map(this::mapToSearchResult)
                        .collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .build();
    }

    /**
     * Map ProductDocument to search result DTO.
     */
    private SearchResultDto.ProductResult mapToSearchResult(ProductDocument doc) {
        return SearchResultDto.ProductResult.builder()
                .id(doc.getId())
                .name(doc.getName())
                .description(doc.getDescription())
                .category(doc.getCategory())
                .price(doc.getPrice())
                .imageUrl(doc.getImageUrl())
                .stockQuantity(doc.getStockQuantity())
                .rating(doc.getRating())
                .reviewCount(doc.getReviewCount())
                .build();
    }
}