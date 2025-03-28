package com.shopsphere.shipping.service;

import java.util.Optional;

import com.shopsphere.shipping.dto.ShipmentDTO;

public interface ShipmentService {
    ShipmentDTO createShipment(ShipmentDTO shipmentDTO);
    ShipmentDTO updateShipment(String id, ShipmentDTO shipmentDTO);
    void deleteShipment(String id);
    Optional<ShipmentDTO> getShipmentById(String id);
    Optional<ShipmentDTO> getShipmentByOrderId(String orderId);
    Optional<ShipmentDTO> getShipmentByTrackingNumber(String trackingNumber);
    ShipmentDTO calculateShippingCost(ShipmentDTO shipmentDTO);
    ShipmentDTO generateShippingLabel(String id);
    ShipmentDTO updateShipmentStatus(String id, String status);
    ShipmentDTO updateTrackingInfo(String id, String trackingNumber, String status);
} 