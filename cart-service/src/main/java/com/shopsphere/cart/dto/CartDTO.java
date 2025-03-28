package com.shopsphere.cart.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartDTO {
    private String id;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotNull(message = "Items list cannot be null")
    private List<CartItemDTO> items;
    
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private String couponCode;
    private BigDecimal discount;
} 