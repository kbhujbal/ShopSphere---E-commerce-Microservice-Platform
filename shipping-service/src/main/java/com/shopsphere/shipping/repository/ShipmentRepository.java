package com.shopsphere.shipping.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.shipping.model.Shipment;

@Repository
public interface ShipmentRepository extends BaseRepository<Shipment> {
    Optional<Shipment> findByOrderId(String orderId);
    Optional<Shipment> findByTrackingNumber(String trackingNumber);
} 