package com.shopsphere.product.controller;

import com.shopsphere.common.response.ApiResponse;
import com.shopsphere.product.dto.ProductDTO;
import com.shopsphere.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(ApiResponse.success(productService.createProduct(productDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, productDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable String id) {
        return productService.getProductById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get a product by SKU")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getAllProducts() {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts()));
    }

    @GetMapping("/page")
    @Operation(summary = "Get all products with pagination")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts(pageable)));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductsByCategory(category)));
    }

    @GetMapping("/brand/{brand}")
    @Operation(summary = "Get products by brand")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getProductsByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductsByBrand(brand)));
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active products")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getActiveProducts() {
        return ResponseEntity.ok(ApiResponse.success(productService.getActiveProducts()));
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Update product stock")
    public ResponseEntity<ApiResponse<Void>> updateStock(
            @PathVariable String id,
            @RequestParam int quantity) {
        productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PutMapping("/{id}/rating")
    @Operation(summary = "Update product rating")
    public ResponseEntity<ApiResponse<Void>> updateRating(
            @PathVariable String id,
            @RequestParam double rating) {
        productService.updateRating(id, rating);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 