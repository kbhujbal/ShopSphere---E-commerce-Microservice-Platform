package com.shopsphere.product.service.impl;

import com.shopsphere.common.exception.BadRequestException;
import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.product.dto.ProductDTO;
import com.shopsphere.product.mapper.ProductMapper;
import com.shopsphere.product.model.Product;
import com.shopsphere.product.repository.ProductRepository;
import com.shopsphere.product.repository.elasticsearch.ProductElasticsearchRepository;
import com.shopsphere.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private ProductElasticsearchRepository elasticsearchRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Autowired(required = false)
    public void setElasticsearchRepository(ProductElasticsearchRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;
        log.info("Elasticsearch repository is available");
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creating new product: {}", productDTO);
        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new BadRequestException("Product with SKU " + productDTO.getSku() + " already exists");
        }
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        indexToElasticsearch(product);
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        Product updatedProduct = productMapper.toEntity(productDTO);
        updatedProduct.setId(existingProduct.getId());
        updatedProduct = productRepository.save(updatedProduct);
        indexToElasticsearch(updatedProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        if (elasticsearchRepository != null) {
            try {
                elasticsearchRepository.deleteById(id);
            } catch (Exception e) {
                log.warn("Failed to delete product from Elasticsearch index: {}", id, e);
            }
        }
    }

    @Override
    public Optional<ProductDTO> getProductById(String id) {
        log.info("Getting product with id: {}", id);
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> getProductBySku(String sku) {
        log.info("Getting product with SKU: {}", sku);
        return productRepository.findBySku(sku)
                .map(productMapper::toDTO);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        log.info("Getting all products");
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        log.info("Getting all products with pagination");
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        log.info("Getting products by category: {}", category);
        return productRepository.findByCategory(category).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByBrand(String brand) {
        log.info("Getting products by brand: {}", brand);
        return productRepository.findByBrand(brand).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getActiveProducts() {
        log.info("Getting active products");
        return productRepository.findByActive(true).stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStock(String id, int quantity) {
        log.info("Updating stock for product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setStockQuantity(quantity);
        product = productRepository.save(product);
        indexToElasticsearch(product);
    }

    @Override
    @Transactional
    public void updateRating(String id, double rating) {
        log.info("Updating rating for product with id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setRating(rating);
        product = productRepository.save(product);
        indexToElasticsearch(product);
    }

    private void indexToElasticsearch(Product product) {
        if (elasticsearchRepository == null) return;
        try {
            elasticsearchRepository.save(product);
        } catch (Exception e) {
            log.warn("Failed to index product in Elasticsearch: {}", product.getId(), e);
        }
    }
} 