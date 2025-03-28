package com.shopsphere.review.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Tag(name = "Review Management", description = "APIs for managing product reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "Create a new review")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing review")
    public ResponseEntity<ReviewDTO> updateReview(
            @Parameter(description = "Review ID") @PathVariable String id,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    public ResponseEntity<Void> deleteReview(
            @Parameter(description = "Review ID") @PathVariable String id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a review by ID")
    public ResponseEntity<ReviewDTO> getReviewById(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get reviews for a product")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByProductId(
            @Parameter(description = "Product ID") @PathVariable String productId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId, pageable));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reviews by a user")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByUserId(
            @Parameter(description = "User ID") @PathVariable String userId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId, pageable));
    }

    @GetMapping("/product/{productId}/verified")
    @Operation(summary = "Get verified reviews for a product")
    public ResponseEntity<Page<ReviewDTO>> getVerifiedReviewsByProductId(
            @Parameter(description = "Product ID") @PathVariable String productId,
            Pageable pageable) {
        return ResponseEntity.ok(reviewService.getVerifiedReviewsByProductId(productId, pageable));
    }

    @GetMapping("/order-item/{orderItemId}")
    @Operation(summary = "Get review for an order item")
    public ResponseEntity<ReviewDTO> getReviewByOrderItemId(
            @Parameter(description = "Order Item ID") @PathVariable String orderItemId) {
        return reviewService.getReviewByOrderItemId(orderItemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve a review")
    public ResponseEntity<ReviewDTO> approveReview(
            @Parameter(description = "Review ID") @PathVariable String id,
            @RequestBody String moderatorNotes) {
        return ResponseEntity.ok(reviewService.approveReview(id, moderatorNotes));
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject a review")
    public ResponseEntity<ReviewDTO> rejectReview(
            @Parameter(description = "Review ID") @PathVariable String id,
            @RequestBody String rejectionReason) {
        return ResponseEntity.ok(reviewService.rejectReview(id, rejectionReason));
    }

    @PutMapping("/{id}/helpful")
    @Operation(summary = "Mark a review as helpful")
    public ResponseEntity<ReviewDTO> markAsHelpful(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.markAsHelpful(id));
    }

    @PutMapping("/{id}/not-helpful")
    @Operation(summary = "Mark a review as not helpful")
    public ResponseEntity<ReviewDTO> markAsNotHelpful(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.markAsNotHelpful(id));
    }

    @PutMapping("/{id}/like")
    @Operation(summary = "Like a review")
    public ResponseEntity<ReviewDTO> likeReview(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.likeReview(id));
    }

    @PutMapping("/{id}/dislike")
    @Operation(summary = "Dislike a review")
    public ResponseEntity<ReviewDTO> dislikeReview(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.dislikeReview(id));
    }

    @PutMapping("/{id}/feature")
    @Operation(summary = "Feature a review")
    public ResponseEntity<ReviewDTO> featureReview(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.featureReview(id));
    }

    @PutMapping("/{id}/unfeature")
    @Operation(summary = "Unfeature a review")
    public ResponseEntity<ReviewDTO> unfeatureReview(
            @Parameter(description = "Review ID") @PathVariable String id) {
        return ResponseEntity.ok(reviewService.unfeatureReview(id));
    }

    @GetMapping("/product/{productId}/featured")
    @Operation(summary = "Get featured reviews for a product")
    public ResponseEntity<List<ReviewDTO>> getFeaturedReviewsByProductId(
            @Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getFeaturedReviewsByProductId(productId));
    }

    @GetMapping("/pending")
    @Operation(summary = "Get pending reviews")
    public ResponseEntity<Page<ReviewDTO>> getPendingReviews(Pageable pageable) {
        return ResponseEntity.ok(reviewService.getPendingReviews(pageable));
    }

    @GetMapping("/product/{productId}/average-rating")
    @Operation(summary = "Calculate average rating for a product")
    public ResponseEntity<Double> calculateAverageRating(
            @Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(reviewService.calculateAverageRating(productId));
    }

    @GetMapping("/product/{productId}/count")
    @Operation(summary = "Get review count for a product")
    public ResponseEntity<Integer> getReviewCount(
            @Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getReviewCount(productId));
    }

    @GetMapping("/product/{productId}/verified-count")
    @Operation(summary = "Get verified review count for a product")
    public ResponseEntity<Integer> getVerifiedReviewCount(
            @Parameter(description = "Product ID") @PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getVerifiedReviewCount(productId));
    }
} 