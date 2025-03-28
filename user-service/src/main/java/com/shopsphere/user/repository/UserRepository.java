package com.shopsphere.user.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.user.model.User;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 