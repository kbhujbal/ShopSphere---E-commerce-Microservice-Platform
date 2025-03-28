package com.shopsphere.payment.service;

import java.util.Optional;

import com.shopsphere.payment.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO createPayment(PaymentDTO paymentDTO);
    PaymentDTO updatePayment(String id, PaymentDTO paymentDTO);
    void deletePayment(String id);
    Optional<PaymentDTO> getPaymentById(String id);
    Optional<PaymentDTO> getPaymentByOrderId(String orderId);
    Optional<PaymentDTO> getPaymentByPaymentIntentId(String paymentIntentId);
    PaymentDTO processPayment(PaymentDTO paymentDTO);
    PaymentDTO refundPayment(String id, String reason);
    PaymentDTO cancelPayment(String id);
    PaymentDTO confirmPayment(String id);
} 