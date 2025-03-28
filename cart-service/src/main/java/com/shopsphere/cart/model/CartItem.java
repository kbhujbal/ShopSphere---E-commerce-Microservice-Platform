package com.shopsphere.cart.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private String productName;
    private String sku;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private String imageUrl;
} 