package com.shopsphere.user.controller;

import java.util.List;

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

import com.shopsphere.common.response.ApiResponse;
import com.shopsphere.user.dto.UserDTO;
import com.shopsphere.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(ApiResponse.success(userService.createUser(userDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(id, userDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by ID")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get a user by username")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a user by email")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers()));
    }

    @PutMapping("/{id}/password")
    @Operation(summary = "Change user password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable String id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam String email) {
        userService.resetPassword(email);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Verify user email")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 