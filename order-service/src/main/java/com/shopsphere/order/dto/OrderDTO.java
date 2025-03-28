package com.shopsphere.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private String id;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    private String orderNumber;
    
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemDTO> items;
    
    @NotNull(message = "Subtotal is required")
    private BigDecimal subtotal;
    
    @NotNull(message = "Tax is required")
    private BigDecimal tax;
    
    @NotNull(message = "Shipping cost is required")
    private BigDecimal shippingCost;
    
    @NotNull(message = "Total is required")
    private BigDecimal total;
    
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    
    @NotBlank(message = "Billing address is required")
    private String billingAddress;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private String paymentStatus;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private String trackingNumber;
    private String notes;
} 