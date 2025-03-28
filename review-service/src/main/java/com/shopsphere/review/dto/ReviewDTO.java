package com.shopsphere.review.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDTO {
    private String id;
    
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Product ID is required")
    private String productId;
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotBlank(message = "Order item ID is required")
    private String orderItemId;
    
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private int rating;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private List<String> images;
    private boolean verifiedPurchase;
    private boolean helpful;
    private int helpfulCount;
    private String status;
    private String moderatorNotes;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private String rejectionReason;
    private boolean featured;
    private int likes;
    private int dislikes;
    private List<String> tags;
    private String pros;
    private String cons;
    private boolean anonymous;
    private String userNickname;
    private String userLocation;
    private LocalDateTime purchaseDate;
    private String productVariant;
    private String productSize;
    private String productColor;
} 