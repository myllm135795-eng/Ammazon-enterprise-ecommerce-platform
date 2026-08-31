package com.ammazon.product.repository;

import com.ammazon.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Product repository for MongoDB operations.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
    List<Product> findByCategory(String category);
    List<Product> findByActiveTrue();
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    List<Product> searchByName(String name);
}