package com.shopsphere.shipping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.common.response.ApiResponse;
import com.shopsphere.shipping.dto.ShipmentDTO;
import com.shopsphere.shipping.service.ShipmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipment Controller", description = "APIs for managing shipments")
public class ShipmentController {
    private final ShipmentService shipmentService;

    @PostMapping
    @Operation(summary = "Create a new shipment")
    public ResponseEntity<ApiResponse<ShipmentDTO>> createShipment(@Valid @RequestBody ShipmentDTO shipmentDTO) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.createShipment(shipmentDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing shipment")
    public ResponseEntity<ApiResponse<ShipmentDTO>> updateShipment(
            @PathVariable String id,
            @Valid @RequestBody ShipmentDTO shipmentDTO) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.updateShipment(id, shipmentDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shipment")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable String id) {
        shipmentService.deleteShipment(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a shipment by ID")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentById(@PathVariable String id) {
        return shipmentService.getShipmentById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get a shipment by order ID")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByOrderId(@PathVariable String orderId) {
        return shipmentService.getShipmentByOrderId(orderId)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tracking/{trackingNumber}")
    @Operation(summary = "Get a shipment by tracking number")
    public ResponseEntity<ApiResponse<ShipmentDTO>> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return shipmentService.getShipmentByTrackingNumber(trackingNumber)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/calculate-cost")
    @Operation(summary = "Calculate shipping cost")
    public ResponseEntity<ApiResponse<ShipmentDTO>> calculateShippingCost(@Valid @RequestBody ShipmentDTO shipmentDTO) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.calculateShippingCost(shipmentDTO)));
    }

    @PostMapping("/{id}/generate-label")
    @Operation(summary = "Generate shipping label")
    public ResponseEntity<ApiResponse<ShipmentDTO>> generateShippingLabel(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.generateShippingLabel(id)));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update shipment status")
    public ResponseEntity<ApiResponse<ShipmentDTO>> updateShipmentStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.updateShipmentStatus(id, status)));
    }

    @PutMapping("/{id}/tracking")
    @Operation(summary = "Update tracking information")
    public ResponseEntity<ApiResponse<ShipmentDTO>> updateTrackingInfo(
            @PathVariable String id,
            @RequestParam String trackingNumber,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success(shipmentService.updateTrackingInfo(id, trackingNumber, status)));
    }
} 