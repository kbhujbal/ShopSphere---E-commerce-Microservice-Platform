package com.shopsphere.shipping.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "shipments")
public class Shipment extends BaseEntity {
    private String orderId;
    private String userId;
    private String trackingNumber;
    private String carrier;
    private String serviceType;
    private BigDecimal weight;
    private String weightUnit;
    private BigDecimal shippingCost;
    private String status;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private String originAddress;
    private String originCity;
    private String originState;
    private String originCountry;
    private String originPostalCode;
    private String destinationAddress;
    private String destinationCity;
    private String destinationState;
    private String destinationCountry;
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