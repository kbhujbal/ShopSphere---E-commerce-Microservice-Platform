package com.shopsphere.payment.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.payment.dto.PaymentDTO;
import com.shopsphere.payment.mapper.PaymentMapper;
import com.shopsphere.payment.model.Payment;
import com.shopsphere.payment.repository.PaymentRepository;
import com.shopsphere.payment.service.PaymentService;
import com.shopsphere.common.exception.ResourceNotFoundException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Override
    @Transactional
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        log.info("Creating new payment for order: {}", paymentDTO.getOrderId());
        Payment payment = paymentMapper.toEntity(paymentDTO);
        payment = paymentRepository.save(payment);
        return paymentMapper.toDTO(payment);
    }

    @Override
    @Transactional
    public PaymentDTO updatePayment(String id, PaymentDTO paymentDTO) {
        log.info("Updating payment with id: {}", id);
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
        
        Payment updatedPayment = paymentMapper.toEntity(paymentDTO);
        updatedPayment.setId(existingPayment.getId());
        updatedPayment = paymentRepository.save(updatedPayment);
        return paymentMapper.toDTO(updatedPayment);
    }

    @Override
    @Transactional
    public void deletePayment(String id) {
        log.info("Deleting payment with id: {}", id);
        if (!paymentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public Optional<PaymentDTO> getPaymentById(String id) {
        log.info("Getting payment with id: {}", id);
        return paymentRepository.findById(id)
                .map(paymentMapper::toDTO);
    }

    @Override
    public Optional<PaymentDTO> getPaymentByOrderId(String orderId) {
        log.info("Getting payment for order: {}", orderId);
        return paymentRepository.findByOrderId(orderId)
                .map(paymentMapper::toDTO);
    }

    @Override
    public Optional<PaymentDTO> getPaymentByPaymentIntentId(String paymentIntentId) {
        log.info("Getting payment with payment intent id: {}", paymentIntentId);
        return paymentRepository.findByPaymentIntentId(paymentIntentId)
                .map(paymentMapper::toDTO);
    }

    @Override
    @Transactional
    public PaymentDTO processPayment(PaymentDTO paymentDTO) {
        log.info("Processing payment for order: {}", paymentDTO.getOrderId());
        Stripe.apiKey = stripeSecretKey;

        try {
            // Create a PaymentIntent with the order amount and currency
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentDTO.getAmount().multiply(java.math.BigDecimal.valueOf(100)).longValue())
                    .setCurrency(paymentDTO.getCurrency().toLowerCase())
                    .setPaymentMethod(paymentDTO.getPaymentMethodId())
                    .setConfirm(true)
                    .setCustomer(paymentDTO.getCustomerId())
                    .setDescription(paymentDTO.getDescription())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            // Update payment record with payment intent details
            Payment payment = paymentMapper.toEntity(paymentDTO);
            payment.setPaymentIntentId(paymentIntent.getId());
            payment.setStatus(paymentIntent.getStatus());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setReceiptUrl(paymentIntent.getCharges().getData().get(0).getReceiptUrl());
            payment = paymentRepository.save(payment);

            return paymentMapper.toDTO(payment);
        } catch (StripeException e) {
            log.error("Error processing payment: {}", e.getMessage());
            Payment payment = paymentMapper.toEntity(paymentDTO);
            payment.setStatus("failed");
            payment.setErrorMessage(e.getMessage());
            payment.setErrorCode(e.getCode());
            payment = paymentRepository.save(payment);
            return paymentMapper.toDTO(payment);
        }
    }

    @Override
    @Transactional
    public PaymentDTO refundPayment(String id, String reason) {
        log.info("Refunding payment with id: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        Stripe.apiKey = stripeSecretKey;

        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(payment.getPaymentIntentId())
                    .setReason(RefundCreateParams.Reason.REQUESTED_BY_CUSTOMER)
                    .build();

            Refund refund = Refund.create(params);

            payment.setStatus("refunded");
            payment.setDescription("Refunded: " + reason);
            payment = paymentRepository.save(payment);

            return paymentMapper.toDTO(payment);
        } catch (StripeException e) {
            log.error("Error refunding payment: {}", e.getMessage());
            payment.setStatus("refund_failed");
            payment.setErrorMessage(e.getMessage());
            payment.setErrorCode(e.getCode());
            payment = paymentRepository.save(payment);
            return paymentMapper.toDTO(payment);
        }
    }

    @Override
    @Transactional
    public PaymentDTO cancelPayment(String id) {
        log.info("Cancelling payment with id: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentIntentId());
            paymentIntent.cancel();

            payment.setStatus("cancelled");
            payment = paymentRepository.save(payment);

            return paymentMapper.toDTO(payment);
        } catch (StripeException e) {
            log.error("Error cancelling payment: {}", e.getMessage());
            payment.setStatus("cancel_failed");
            payment.setErrorMessage(e.getMessage());
            payment.setErrorCode(e.getCode());
            payment = paymentRepository.save(payment);
            return paymentMapper.toDTO(payment);
        }
    }

    @Override
    @Transactional
    public PaymentDTO confirmPayment(String id) {
        log.info("Confirming payment with id: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(payment.getPaymentIntentId());
            paymentIntent.confirm();

            payment.setStatus("confirmed");
            payment = paymentRepository.save(payment);

            return paymentMapper.toDTO(payment);
        } catch (StripeException e) {
            log.error("Error confirming payment: {}", e.getMessage());
            payment.setStatus("confirm_failed");
            payment.setErrorMessage(e.getMessage());
            payment.setErrorCode(e.getCode());
            payment = paymentRepository.save(payment);
            return paymentMapper.toDTO(payment);
        }
    }
} 