package com.shopsphere.review.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shopsphere.review.dto.ReviewDTO;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(String id, ReviewDTO reviewDTO);
    void deleteReview(String id);
    Optional<ReviewDTO> getReviewById(String id);
    Page<ReviewDTO> getReviewsByProductId(String productId, Pageable pageable);
    Page<ReviewDTO> getReviewsByUserId(String userId, Pageable pageable);
    Page<ReviewDTO> getVerifiedReviewsByProductId(String productId, Pageable pageable);
    Optional<ReviewDTO> getReviewByOrderItemId(String orderItemId);
    ReviewDTO approveReview(String id, String moderatorNotes);
    ReviewDTO rejectReview(String id, String rejectionReason);
    ReviewDTO markAsHelpful(String id);
    ReviewDTO markAsNotHelpful(String id);
    ReviewDTO likeReview(String id);
    ReviewDTO dislikeReview(String id);
    ReviewDTO featureReview(String id);
    ReviewDTO unfeatureReview(String id);
    List<ReviewDTO> getFeaturedReviewsByProductId(String productId);
    Page<ReviewDTO> getPendingReviews(Pageable pageable);
    double calculateAverageRating(String productId);
    int getReviewCount(String productId);
    int getVerifiedReviewCount(String productId);
} 