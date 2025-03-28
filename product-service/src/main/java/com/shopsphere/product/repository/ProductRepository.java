package com.shopsphere.product.repository;

import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product> {
    Optional<Product> findBySku(String sku);
    List<Product> findByCategory(String category);
    List<Product> findByBrand(String brand);
    List<Product> findByActive(boolean active);
    boolean existsBySku(String sku);
} 