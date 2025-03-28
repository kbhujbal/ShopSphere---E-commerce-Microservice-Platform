package com.shopsphere.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.cart.dto.CartDTO;
import com.shopsphere.cart.dto.CartItemDTO;
import com.shopsphere.cart.service.CartService;
import com.shopsphere.common.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart Controller", description = "APIs for managing shopping carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Create a new cart")
    public ResponseEntity<ApiResponse<CartDTO>> createCart(@Valid @RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(ApiResponse.success(cartService.createCart(cartDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing cart")
    public ResponseEntity<ApiResponse<CartDTO>> updateCart(
            @PathVariable String id,
            @Valid @RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(ApiResponse.success(cartService.updateCart(id, cartDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cart")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable String id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cart by ID")
    public ResponseEntity<ApiResponse<CartDTO>> getCartById(@PathVariable String id) {
        return cartService.getCartById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get a cart by user ID")
    public ResponseEntity<ApiResponse<CartDTO>> getCartByUserId(@PathVariable String userId) {
        return cartService.getCartByUserId(userId)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{userId}/items")
    @Operation(summary = "Add an item to cart")
    public ResponseEntity<ApiResponse<CartDTO>> addItemToCart(
            @PathVariable String userId,
            @Valid @RequestBody CartItemDTO itemDTO) {
        return ResponseEntity.ok(ApiResponse.success(cartService.addItemToCart(userId, itemDTO)));
    }

    @PutMapping("/user/{userId}/items/{productId}")
    @Operation(summary = "Update item quantity in cart")
    public ResponseEntity<ApiResponse<CartDTO>> updateItemQuantity(
            @PathVariable String userId,
            @PathVariable String productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(ApiResponse.success(cartService.updateItemQuantity(userId, productId, quantity)));
    }

    @DeleteMapping("/user/{userId}/items/{productId}")
    @Operation(summary = "Remove an item from cart")
    public ResponseEntity<ApiResponse<CartDTO>> removeItemFromCart(
            @PathVariable String userId,
            @PathVariable String productId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.removeItemFromCart(userId, productId)));
    }

    @DeleteMapping("/user/{userId}")
    @Operation(summary = "Clear cart")
    public ResponseEntity<ApiResponse<Void>> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/user/{userId}/coupon")
    @Operation(summary = "Apply coupon to cart")
    public ResponseEntity<ApiResponse<CartDTO>> applyCoupon(
            @PathVariable String userId,
            @RequestParam String couponCode) {
        return ResponseEntity.ok(ApiResponse.success(cartService.applyCoupon(userId, couponCode)));
    }

    @DeleteMapping("/user/{userId}/coupon")
    @Operation(summary = "Remove coupon from cart")
    public ResponseEntity<ApiResponse<CartDTO>> removeCoupon(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(cartService.removeCoupon(userId)));
    }
} 