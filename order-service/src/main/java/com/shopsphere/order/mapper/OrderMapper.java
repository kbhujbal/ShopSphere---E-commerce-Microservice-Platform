package com.shopsphere.order.mapper;

import com.shopsphere.order.dto.OrderDTO;
import com.shopsphere.order.dto.OrderItemDTO;
import com.shopsphere.order.model.Order;
import com.shopsphere.order.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderDTO orderDTO);

    OrderDTO toOrderDTO(Order order);

    List<OrderDTO> toOrderDTOList(List<Order> orders);

    OrderItem toOrderItem(OrderItemDTO orderItemDTO);

    OrderItemDTO toOrderItemDTO(OrderItem orderItem);
} 