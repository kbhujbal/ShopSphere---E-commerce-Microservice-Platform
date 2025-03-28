package com.shopsphere.order.service;

import com.shopsphere.order.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO getOrderById(String id);
    OrderDTO getOrderByOrderNumber(String orderNumber);
    List<OrderDTO> getOrdersByUserId(String userId);
    List<OrderDTO> getOrdersByStatus(String orderStatus);
    OrderDTO updateOrderStatus(String id, String orderStatus);
    OrderDTO updateOrder(String id, OrderDTO orderDTO);
    void deleteOrder(String id);
} 