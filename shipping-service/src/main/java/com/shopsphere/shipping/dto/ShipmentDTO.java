package com.shopsphere.shipping.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ShipmentDTO {
    private String id;
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    private String trackingNumber;
    
    @NotBlank(message = "Carrier is required")
    private String carrier;
    
    @NotBlank(message = "Service type is required")
    private String serviceType;
    
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be greater than zero")
    private BigDecimal weight;
    
    @NotBlank(message = "Weight unit is required")
    private String weightUnit;
    
    private BigDecimal shippingCost;
    private String status;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    
    @NotBlank(message = "Origin address is required")
    private String originAddress;
    
    @NotBlank(message = "Origin city is required")
    private String originCity;
    
    @NotBlank(message = "Origin state is required")
    private String originState;
    
    @NotBlank(message = "Origin country is required")
    private String originCountry;
    
    @NotBlank(message = "Origin postal code is required")
    private String originPostalCode;
    
    @NotBlank(message = "Destination address is required")
    private String destinationAddress;
    
    @NotBlank(message = "Destination city is required")
    private String destinationCity;
    
    @NotBlank(message = "Destination state is required")
    private String destinationState;
    
    @NotBlank(message = "Destination country is required")
    private String destinationCountry;
    
    @NotBlank(message = "Destination postal code is required")
    private String destinationPostalCode;
    
    private String notes;
    private String labelUrl;
    private String returnLabelUrl;
    private boolean signatureRequired;
    private boolean insuranceRequired;
    private BigDecimal declaredValue;
    private String customsInfo;
    private String customsValue;
    private String customsCurrency;
} 