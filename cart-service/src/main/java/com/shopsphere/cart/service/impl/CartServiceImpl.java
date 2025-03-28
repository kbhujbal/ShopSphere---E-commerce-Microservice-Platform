package com.shopsphere.cart.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.cart.dto.CartDTO;
import com.shopsphere.cart.dto.CartItemDTO;
import com.shopsphere.cart.mapper.CartMapper;
import com.shopsphere.cart.model.Cart;
import com.shopsphere.cart.model.CartItem;
import com.shopsphere.cart.repository.CartRepository;
import com.shopsphere.cart.service.CartService;
import com.shopsphere.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartDTO createCart(CartDTO cartDTO) {
        log.info("Creating new cart for user: {}", cartDTO.getUserId());
        Cart cart = cartMapper.toEntity(cartDTO);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateCart(String id, CartDTO cartDTO) {
        log.info("Updating cart with id: {}", id);
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
        
        Cart updatedCart = cartMapper.toEntity(cartDTO);
        updatedCart.setId(existingCart.getId());
        updatedCart = cartRepository.save(updatedCart);
        return cartMapper.toDTO(updatedCart);
    }

    @Override
    @Transactional
    public void deleteCart(String id) {
        log.info("Deleting cart with id: {}", id);
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }

    @Override
    public Optional<CartDTO> getCartById(String id) {
        log.info("Getting cart with id: {}", id);
        return cartRepository.findById(id)
                .map(cartMapper::toDTO);
    }

    @Override
    public Optional<CartDTO> getCartByUserId(String userId) {
        log.info("Getting cart for user: {}", userId);
        return cartRepository.findByUserId(userId)
                .map(cartMapper::toDTO);
    }

    @Override
    @Transactional
    public CartDTO addItemToCart(String userId, CartItemDTO itemDTO) {
        log.info("Adding item to cart for user: {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    return newCart;
                });

        CartItem cartItem = new CartItem();
        cartItem.setProductId(itemDTO.getProductId());
        cartItem.setProductName(itemDTO.getProductName());
        cartItem.setSku(itemDTO.getSku());
        cartItem.setQuantity(itemDTO.getQuantity());
        cartItem.setUnitPrice(itemDTO.getUnitPrice());
        cartItem.setSubtotal(itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
        cartItem.setImageUrl(itemDTO.getImageUrl());

        cart.getItems().add(cartItem);
        updateCartTotals(cart);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO updateItemQuantity(String userId, String productId, int quantity) {
        log.info("Updating item quantity in cart for user: {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setSubtotal(item.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
                });

        updateCartTotals(cart);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO removeItemFromCart(String userId, String productId) {
        log.info("Removing item from cart for user: {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        updateCartTotals(cart);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public void clearCart(String userId) {
        log.info("Clearing cart for user: {}", userId);
        cartRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public CartDTO applyCoupon(String userId, String couponCode) {
        log.info("Applying coupon to cart for user: {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cart.setCouponCode(couponCode);
        // TODO: Implement coupon validation and discount calculation
        cart.setDiscount(BigDecimal.ZERO);
        updateCartTotals(cart);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    @Override
    @Transactional
    public CartDTO removeCoupon(String userId) {
        log.info("Removing coupon from cart for user: {}", userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));

        cart.setCouponCode(null);
        cart.setDiscount(BigDecimal.ZERO);
        updateCartTotals(cart);
        cart = cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    private void updateCartTotals(Cart cart) {
        cart.setSubtotal(cart.getItems().stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        // TODO: Implement tax calculation based on location
        cart.setTax(BigDecimal.ZERO);

        // TODO: Implement shipping cost calculation based on location and weight
        cart.setShippingCost(BigDecimal.ZERO);

        cart.setTotal(cart.getSubtotal()
                .add(cart.getTax())
                .add(cart.getShippingCost())
                .subtract(cart.getDiscount()));
    }
} 