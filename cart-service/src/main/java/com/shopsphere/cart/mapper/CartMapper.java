package com.shopsphere.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.shopsphere.cart.dto.CartDTO;
import com.shopsphere.cart.model.Cart;

@Mapper
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Cart toEntity(CartDTO dto);

    CartDTO toDTO(Cart entity);
} 