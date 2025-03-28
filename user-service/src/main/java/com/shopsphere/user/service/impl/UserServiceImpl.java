package com.shopsphere.user.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.common.exception.BadRequestException;
import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.user.dto.UserDTO;
import com.shopsphere.user.mapper.UserMapper;
import com.shopsphere.user.model.User;
import com.shopsphere.user.repository.UserRepository;
import com.shopsphere.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        log.info("Creating new user: {}", userDTO.getUsername());
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(String id, UserDTO userDTO) {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        User updatedUser = userMapper.toEntity(userDTO);
        updatedUser.setId(existingUser.getId());
        updatedUser.setPassword(existingUser.getPassword()); // Keep existing password
        updatedUser = userRepository.save(updatedUser);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> getUserById(String id) {
        log.info("Getting user with id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        log.info("Getting user with username: {}", username);
        return userRepository.findByUsername(username)
                .map(userMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        log.info("Getting user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void changePassword(String id, String oldPassword, String newPassword) {
        log.info("Changing password for user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadRequestException("Invalid old password");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(String email) {
        log.info("Resetting password for user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        
        String newPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        // TODO: Send email with new password
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        // TODO: Implement email verification
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
} 