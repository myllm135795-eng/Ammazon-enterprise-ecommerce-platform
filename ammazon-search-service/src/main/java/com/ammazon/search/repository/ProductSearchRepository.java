package com.ammazon.search.repository;

import com.ammazon.search.document.ProductSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product search repository using Elasticsearch.
 */
@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductSearchDocument, String> {
    List<ProductSearchDocument> findByNameContainingIgnoreCase(String name);
    List<ProductSearchDocument> findByCategory(String category);
    List<ProductSearchDocument> findByActiveTrue();
}