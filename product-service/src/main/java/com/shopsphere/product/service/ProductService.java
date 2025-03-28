package com.shopsphere.product.service;

import com.shopsphere.product.dto.ProductDTO;
import com.shopsphere.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
    Optional<ProductDTO> getProductById(String id);
    Optional<ProductDTO> getProductBySku(String sku);
    List<ProductDTO> getAllProducts();
    Page<ProductDTO> getAllProducts(Pageable pageable);
    List<ProductDTO> getProductsByCategory(String category);
    List<ProductDTO> getProductsByBrand(String brand);
    List<ProductDTO> getActiveProducts();
    void updateStock(String id, int quantity);
    void updateRating(String id, double rating);
} 