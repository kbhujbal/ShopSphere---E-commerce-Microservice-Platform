package com.shopsphere.order.controller;

import com.shopsphere.order.dto.OrderDTO;
import com.shopsphere.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "APIs for managing orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Get order by order number")
    public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        return ResponseEntity.ok(orderService.getOrderByOrderNumber(orderNumber));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get orders by user ID")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/status/{orderStatus}")
    @Operation(summary = "Get orders by status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable String orderStatus) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(orderStatus));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable String id,
            @RequestParam String orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, orderStatus));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable String id,
            @Valid @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete order")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
} 