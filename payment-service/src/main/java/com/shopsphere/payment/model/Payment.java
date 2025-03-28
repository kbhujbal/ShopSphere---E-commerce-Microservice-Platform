package com.shopsphere.payment.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "payments")
public class Payment extends BaseEntity {
    private String orderId;
    private String userId;
    private BigDecimal amount;
    private String currency;
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
    private String billingAddress;
    private String billingCity;
    private String billingState;
    private String billingCountry;
    private String billingPostalCode;
    private String shippingAddress;
    private String shippingCity;
    private String shippingState;
    private String shippingCountry;
    private String shippingPostalCode;
} 