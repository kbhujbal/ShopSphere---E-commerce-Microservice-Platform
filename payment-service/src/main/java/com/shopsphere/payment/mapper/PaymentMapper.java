package com.shopsphere.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shopsphere.payment.dto.PaymentDTO;
import com.shopsphere.payment.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Payment toEntity(PaymentDTO dto);

    PaymentDTO toDTO(Payment entity);
} 