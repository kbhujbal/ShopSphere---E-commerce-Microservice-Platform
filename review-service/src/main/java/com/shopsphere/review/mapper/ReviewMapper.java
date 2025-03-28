package com.shopsphere.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.model.Review;

@Mapper
public interface ReviewMapper {
    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Review toEntity(ReviewDTO dto);

    ReviewDTO toDTO(Review entity);
} 