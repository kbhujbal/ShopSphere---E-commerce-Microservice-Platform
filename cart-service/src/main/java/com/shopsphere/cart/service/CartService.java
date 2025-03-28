package com.shopsphere.cart.service;

import java.util.Optional;

import com.shopsphere.cart.dto.CartDTO;
import com.shopsphere.cart.dto.CartItemDTO;

public interface CartService {
    CartDTO createCart(CartDTO cartDTO);
    CartDTO updateCart(String id, CartDTO cartDTO);
    void deleteCart(String id);
    Optional<CartDTO> getCartById(String id);
    Optional<CartDTO> getCartByUserId(String userId);
    CartDTO addItemToCart(String userId, CartItemDTO itemDTO);
    CartDTO updateItemQuantity(String userId, String productId, int quantity);
    CartDTO removeItemFromCart(String userId, String productId);
    void clearCart(String userId);
    CartDTO applyCoupon(String userId, String couponCode);
    CartDTO removeCoupon(String userId);
} 