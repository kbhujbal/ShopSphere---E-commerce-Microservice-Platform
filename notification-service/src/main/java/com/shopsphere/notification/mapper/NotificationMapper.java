package com.shopsphere.notification.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.shopsphere.notification.dto.NotificationDTO;
import com.shopsphere.notification.model.Notification;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Notification toEntity(NotificationDTO dto);

    NotificationDTO toDTO(Notification entity);
} 