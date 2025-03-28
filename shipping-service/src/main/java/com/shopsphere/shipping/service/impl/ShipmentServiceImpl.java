package com.shopsphere.shipping.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.shipping.dto.ShipmentDTO;
import com.shopsphere.shipping.mapper.ShipmentMapper;
import com.shopsphere.shipping.model.Shipment;
import com.shopsphere.shipping.repository.ShipmentRepository;
import com.shopsphere.shipping.service.ShipmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    @Override
    @Transactional
    public ShipmentDTO createShipment(ShipmentDTO shipmentDTO) {
        log.info("Creating new shipment for order: {}", shipmentDTO.getOrderId());
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment.setStatus("created");
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDTO(shipment);
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipment(String id, ShipmentDTO shipmentDTO) {
        log.info("Updating shipment with id: {}", id);
        Shipment existingShipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        
        Shipment updatedShipment = shipmentMapper.toEntity(shipmentDTO);
        updatedShipment.setId(existingShipment.getId());
        updatedShipment = shipmentRepository.save(updatedShipment);
        return shipmentMapper.toDTO(updatedShipment);
    }

    @Override
    @Transactional
    public void deleteShipment(String id) {
        log.info("Deleting shipment with id: {}", id);
        if (!shipmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Shipment not found with id: " + id);
        }
        shipmentRepository.deleteById(id);
    }

    @Override
    public Optional<ShipmentDTO> getShipmentById(String id) {
        log.info("Getting shipment with id: {}", id);
        return shipmentRepository.findById(id)
                .map(shipmentMapper::toDTO);
    }

    @Override
    public Optional<ShipmentDTO> getShipmentByOrderId(String orderId) {
        log.info("Getting shipment for order: {}", orderId);
        return shipmentRepository.findByOrderId(orderId)
                .map(shipmentMapper::toDTO);
    }

    @Override
    public Optional<ShipmentDTO> getShipmentByTrackingNumber(String trackingNumber) {
        log.info("Getting shipment with tracking number: {}", trackingNumber);
        return shipmentRepository.findByTrackingNumber(trackingNumber)
                .map(shipmentMapper::toDTO);
    }

    @Override
    @Transactional
    public ShipmentDTO calculateShippingCost(ShipmentDTO shipmentDTO) {
        log.info("Calculating shipping cost for order: {}", shipmentDTO.getOrderId());
        // TODO: Implement actual shipping cost calculation logic
        // This would typically involve calling a shipping carrier's API
        Shipment shipment = shipmentMapper.toEntity(shipmentDTO);
        shipment.setShippingCost(calculateShippingCost(shipment));
        return shipmentMapper.toDTO(shipment);
    }

    @Override
    @Transactional
    public ShipmentDTO generateShippingLabel(String id) {
        log.info("Generating shipping label for shipment: {}", id);
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        
        // TODO: Implement actual shipping label generation logic
        // This would typically involve calling a shipping carrier's API
        shipment.setLabelUrl("https://shipping-carrier.com/labels/" + shipment.getTrackingNumber());
        shipment.setStatus("label_generated");
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDTO(shipment);
    }

    @Override
    @Transactional
    public ShipmentDTO updateShipmentStatus(String id, String status) {
        log.info("Updating shipment status for id: {} to: {}", id, status);
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        
        shipment.setStatus(status);
        if ("delivered".equals(status)) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
        }
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDTO(shipment);
    }

    @Override
    @Transactional
    public ShipmentDTO updateTrackingInfo(String id, String trackingNumber, String status) {
        log.info("Updating tracking info for shipment: {} with tracking number: {}", id, trackingNumber);
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        
        shipment.setTrackingNumber(trackingNumber);
        shipment.setStatus(status);
        if ("delivered".equals(status)) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
        }
        shipment = shipmentRepository.save(shipment);
        return shipmentMapper.toDTO(shipment);
    }

    private BigDecimal calculateShippingCost(Shipment shipment) {
        // TODO: Implement actual shipping cost calculation logic
        // This would typically involve calling a shipping carrier's API
        // For now, returning a dummy value
        return BigDecimal.valueOf(10.00);
    }
} 