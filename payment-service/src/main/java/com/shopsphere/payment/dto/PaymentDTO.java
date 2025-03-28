package com.shopsphere.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentDTO {
    private String id;
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private String paymentMethodId;
    private String paymentIntentId;
    private String status;
    private String description;
    private String receiptUrl;
    private LocalDateTime paymentDate;
    private String errorMessage;
    private String errorCode;
    private String customerId;
    private String customerEmail;
    private String customerName;
    
    @NotBlank(message = "Billing address is required")
    private String billingAddress;
    
    @NotBlank(message = "Billing city is required")
    private String billingCity;
    
    @NotBlank(message = "Billing state is required")
    private String billingState;
    
    @NotBlank(message = "Billing country is required")
    private String billingCountry;
    
    @NotBlank(message = "Billing postal code is required")
    private String billingPostalCode;
    
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    
    @NotBlank(message = "Shipping city is required")
    private String shippingCity;
    
    @NotBlank(message = "Shipping state is required")
    private String shippingState;
    
    @NotBlank(message = "Shipping country is required")
    private String shippingCountry;
    
    @NotBlank(message = "Shipping postal code is required")
    private String shippingPostalCode;
} 