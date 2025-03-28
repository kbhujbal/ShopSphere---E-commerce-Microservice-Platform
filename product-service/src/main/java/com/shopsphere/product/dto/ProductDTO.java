package com.shopsphere.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Positive(message = "Stock quantity must be positive")
    private Integer stockQuantity;

    private List<String> images;

    @NotBlank(message = "Brand is required")
    private String brand;

    private Double rating;
    private Integer reviewCount;
    private boolean active;

    @NotBlank(message = "SKU is required")
    private String sku;

    private List<String> tags;
    private List<String> specifications;
} 