package com.shopsphere.review.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.shopsphere.common.model.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "reviews")
public class Review extends BaseEntity {
    private String userId;
    private String productId;
    private String orderId;
    private String orderItemId;
    private int rating;
    private String title;
    private String content;
    private List<String> images;
    private boolean verifiedPurchase;
    private boolean helpful;
    private int helpfulCount;
    private String status; // PENDING, APPROVED, REJECTED
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