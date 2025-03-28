package com.shopsphere.notification.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "notifications")
public class Notification extends BaseEntity {
    private String userId;
    private String type; // EMAIL, SMS, PUSH, IN_APP
    private String title;
    private String content;
    private String status; // PENDING, SENT, FAILED
    private LocalDateTime sentAt;
    private String errorMessage;
    private String recipient; // email address or phone number
    private String templateId;
    private String templateData; // JSON string of template variables
    private boolean read;
    private String actionUrl; // URL for clickable notifications
    private String priority; // HIGH, MEDIUM, LOW
    private String category; // ORDER, PAYMENT, SHIPPING, etc.
    private String source; // Service that triggered the notification
    private String sourceId; // ID of the source entity (order, payment, etc.)
} 