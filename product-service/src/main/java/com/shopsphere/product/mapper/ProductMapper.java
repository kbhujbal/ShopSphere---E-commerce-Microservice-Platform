package com.shopsphere.product.mapper;

import com.shopsphere.product.dto.ProductDTO;
import com.shopsphere.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Product toEntity(ProductDTO dto);

    ProductDTO toDTO(Product entity);
} 