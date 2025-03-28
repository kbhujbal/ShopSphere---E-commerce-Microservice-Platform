package com.shopsphere.order.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    
    private String userId;
    private String orderNumber;
    private List<OrderItem> items;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
    private String paymentStatus;
    private String orderStatus;
    private LocalDateTime orderDate;
    private LocalDateTime estimatedDeliveryDate;
    private String trackingNumber;
    private String notes;
} 