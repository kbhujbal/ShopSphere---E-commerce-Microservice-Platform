package com.shopsphere.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.shopsphere.user.dto.UserDTO;

public interface UserService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
    Optional<UserDTO> getUserById(String id);
    Optional<UserDTO> getUserByUsername(String username);
    Optional<UserDTO> getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    void changePassword(String id, String oldPassword, String newPassword);
    void resetPassword(String email);
    void verifyEmail(String token);
} 