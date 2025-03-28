package com.shopsphere.order.service.impl;

import com.shopsphere.order.dto.OrderDTO;
import com.shopsphere.order.exception.OrderNotFoundException;
import com.shopsphere.order.mapper.OrderMapper;
import com.shopsphere.order.model.Order;
import com.shopsphere.order.repository.OrderRepository;
import com.shopsphere.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toOrder(orderDTO);
        order.setOrderNumber(generateOrderNumber());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        order.setPaymentStatus("PENDING");
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO getOrderById(String id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    @Override
    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(orderMapper::toOrderDTO)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with order number: " + orderNumber));
    }

    @Override
    public List<OrderDTO> getOrdersByUserId(String userId) {
        return orderMapper.toOrderDTOList(orderRepository.findByUserId(userId));
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(String orderStatus) {
        return orderMapper.toOrderDTOList(orderRepository.findByOrderStatus(orderStatus));
    }

    @Override
    @Transactional
    public OrderDTO updateOrderStatus(String id, String orderStatus) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        order.setOrderStatus(orderStatus);
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(String id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
        Order updatedOrder = orderMapper.toOrder(orderDTO);
        updatedOrder.setId(id);
        return orderMapper.toOrderDTO(orderRepository.save(updatedOrder));
    }

    @Override
    @Transactional
    public void deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 