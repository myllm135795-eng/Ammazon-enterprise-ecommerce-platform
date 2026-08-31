package com.ammazon.search.repository;

import com.ammazon.search.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Elasticsearch repository for product search.
 */
@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {
    Page<ProductDocument> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<ProductDocument> findByCategory(String category, Pageable pageable);
    Page<ProductDocument> findByActiveTrue(Pageable pageable);
}