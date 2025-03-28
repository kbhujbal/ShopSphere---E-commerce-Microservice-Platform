package com.shopsphere.cart.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shopsphere.cart.model.Cart;
import com.shopsphere.common.repository.BaseRepository;

@Repository
public interface CartRepository extends BaseRepository<Cart> {
    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);
} 