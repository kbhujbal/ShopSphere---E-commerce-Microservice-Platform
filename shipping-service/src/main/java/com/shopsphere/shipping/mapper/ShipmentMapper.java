package com.shopsphere.shipping.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.shopsphere.shipping.dto.ShipmentDTO;
import com.shopsphere.shipping.model.Shipment;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Shipment toEntity(ShipmentDTO dto);

    ShipmentDTO toDTO(Shipment entity);
} 