package com.shopsphere.notification.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationDTO {
    private String id;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Notification type is required")
    private String type;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String status;
    private LocalDateTime sentAt;
    private String errorMessage;
    
    @NotBlank(message = "Recipient is required")
    private String recipient;
    
    private String templateId;
    private String templateData;
    private boolean read;
    private String actionUrl;
    
    @NotBlank(message = "Priority is required")
    private String priority;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotBlank(message = "Source is required")
    private String source;
    
    @NotBlank(message = "Source ID is required")
    private String sourceId;
} 