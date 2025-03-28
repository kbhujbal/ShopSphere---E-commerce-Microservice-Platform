package com.shopsphere.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.shopsphere.payment.dto.PaymentDTO;
import com.shopsphere.payment.model.Payment;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Payment toEntity(PaymentDTO dto);

    PaymentDTO toDTO(Payment entity);
} 