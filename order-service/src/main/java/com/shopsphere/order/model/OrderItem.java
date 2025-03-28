package com.shopsphere.order.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItem {
    private String productId;
    private String productName;
    private String sku;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String imageUrl;
} 