package com.shopsphere.payment.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.payment.model.Payment;

@Repository
public interface PaymentRepository extends BaseRepository<Payment> {
    Optional<Payment> findByOrderId(String orderId);
    Optional<Payment> findByPaymentIntentId(String paymentIntentId);
} 