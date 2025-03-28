package com.shopsphere.cart.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "carts")
public class Cart extends BaseEntity {
    private String userId;
    private List<CartItem> items = new ArrayList<>();
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    private BigDecimal shippingCost = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;
    private String couponCode;
    private BigDecimal discount = BigDecimal.ZERO;
} 