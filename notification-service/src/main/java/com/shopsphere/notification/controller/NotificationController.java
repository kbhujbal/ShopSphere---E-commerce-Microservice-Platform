package com.shopsphere.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.common.response.ApiResponse;
import com.shopsphere.notification.dto.NotificationDTO;
import com.shopsphere.notification.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Controller", description = "APIs for managing notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Create a new notification")
    public ResponseEntity<ApiResponse<NotificationDTO>> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.createNotification(notificationDTO)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing notification")
    public ResponseEntity<ApiResponse<NotificationDTO>> updateNotification(
            @PathVariable String id,
            @Valid @RequestBody NotificationDTO notificationDTO) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.updateNotification(id, notificationDTO)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a notification by ID")
    public ResponseEntity<ApiResponse<NotificationDTO>> getNotificationById(@PathVariable String id) {
        return notificationService.getNotificationById(id)
                .map(ApiResponse::success)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all notifications for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getNotificationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.getNotificationsByUserId(userId)));
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Get unread notifications for a user")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUnreadNotificationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.getUnreadNotificationsByUserId(userId)));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read")
    public ResponseEntity<ApiResponse<NotificationDTO>> markAsRead(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.markAsRead(id)));
    }

    @PutMapping("/user/{userId}/read-all")
    @Operation(summary = "Mark all notifications as read for a user")
    public ResponseEntity<ApiResponse<NotificationDTO>> markAllAsRead(@PathVariable String userId) {
        return ResponseEntity.ok(ApiResponse.success(notificationService.markAllAsRead(userId)));
    }
} 