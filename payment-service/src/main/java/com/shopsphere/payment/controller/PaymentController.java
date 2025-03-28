package com.shopsphere.payment.controller;

import java.util.Optional;

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

import com.shopsphere.payment.dto.PaymentDTO;
import com.shopsphere.payment.service.PaymentService;
import com.shopsphere.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "APIs for managing payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "Create a new payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> createPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.createPayment(paymentDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> updatePayment(
            @PathVariable String id,
            @Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.updatePayment(id, paymentDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a payment")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a payment by ID")
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentById(@PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Get a payment by order ID")
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentByOrderId(@PathVariable String orderId) {
        return paymentService.getPaymentByOrderId(orderId)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/intent/{paymentIntentId}")
    @Operation(summary = "Get a payment by payment intent ID")
    public ResponseEntity<ApiResponse<PaymentDTO>> getPaymentByPaymentIntentId(@PathVariable String paymentIntentId) {
        return paymentService.getPaymentByPaymentIntentId(paymentIntentId)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/process")
    @Operation(summary = "Process a payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> processPayment(@Valid @RequestBody PaymentDTO paymentDTO) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.processPayment(paymentDTO)));
    }

    @PostMapping("/{id}/refund")
    @Operation(summary = "Refund a payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> refundPayment(
            @PathVariable String id,
            @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.refundPayment(id, reason)));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel a payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> cancelPayment(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.cancelPayment(id)));
    }

    @PostMapping("/{id}/confirm")
    @Operation(summary = "Confirm a payment")
    public ResponseEntity<ApiResponse<PaymentDTO>> confirmPayment(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(paymentService.confirmPayment(id)));
    }
} 