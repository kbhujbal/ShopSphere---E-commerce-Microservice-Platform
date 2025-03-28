package com.shopsphere.review.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.mapper.ReviewMapper;
import com.shopsphere.review.model.Review;
import com.shopsphere.review.repository.ReviewRepository;
import com.shopsphere.review.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        log.info("Creating new review for product: {}", reviewDTO.getProductId());
        Review review = reviewMapper.toEntity(reviewDTO);
        review.setStatus("PENDING");
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO updateReview(String id, ReviewDTO reviewDTO) {
        log.info("Updating review with id: {}", id);
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        Review updatedReview = reviewMapper.toEntity(reviewDTO);
        updatedReview.setId(existingReview.getId());
        updatedReview = reviewRepository.save(updatedReview);
        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    @Transactional
    public void deleteReview(String id) {
        log.info("Deleting review with id: {}", id);
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Override
    public Optional<ReviewDTO> getReviewById(String id) {
        log.info("Getting review with id: {}", id);
        return reviewRepository.findById(id)
                .map(reviewMapper::toDTO);
    }

    @Override
    public Page<ReviewDTO> getReviewsByProductId(String productId, Pageable pageable) {
        log.info("Getting reviews for product: {}", productId);
        return reviewRepository.findByProductIdAndStatusAndDeletedFalseOrderByCreatedAtDesc(productId, "APPROVED", pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    public Page<ReviewDTO> getReviewsByUserId(String userId, Pageable pageable) {
        log.info("Getting reviews for user: {}", userId);
        return reviewRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(userId, pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    public Page<ReviewDTO> getVerifiedReviewsByProductId(String productId, Pageable pageable) {
        log.info("Getting verified reviews for product: {}", productId);
        return reviewRepository.findByProductIdAndVerifiedPurchaseAndStatusAndDeletedFalseOrderByCreatedAtDesc(productId, true, "APPROVED", pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    public Optional<ReviewDTO> getReviewByOrderItemId(String orderItemId) {
        log.info("Getting review for order item: {}", orderItemId);
        return reviewRepository.findByOrderItemIdAndDeletedFalse(orderItemId)
                .map(reviewMapper::toDTO);
    }

    @Override
    @Transactional
    public ReviewDTO approveReview(String id, String moderatorNotes) {
        log.info("Approving review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setStatus("APPROVED");
        review.setModeratorNotes(moderatorNotes);
        review.setApprovedAt(LocalDateTime.now());
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO rejectReview(String id, String rejectionReason) {
        log.info("Rejecting review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setStatus("REJECTED");
        review.setRejectionReason(rejectionReason);
        review.setRejectedAt(LocalDateTime.now());
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO markAsHelpful(String id) {
        log.info("Marking review as helpful: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setHelpful(true);
        review.setHelpfulCount(review.getHelpfulCount() + 1);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO markAsNotHelpful(String id) {
        log.info("Marking review as not helpful: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setHelpful(false);
        review.setHelpfulCount(Math.max(0, review.getHelpfulCount() - 1));
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO likeReview(String id) {
        log.info("Liking review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setLikes(review.getLikes() + 1);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO dislikeReview(String id) {
        log.info("Disliking review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setDislikes(review.getDislikes() + 1);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO featureReview(String id) {
        log.info("Featuring review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setFeatured(true);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    @Transactional
    public ReviewDTO unfeatureReview(String id) {
        log.info("Unfeaturing review: {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        
        review.setFeatured(false);
        review = reviewRepository.save(review);
        return reviewMapper.toDTO(review);
    }

    @Override
    public List<ReviewDTO> getFeaturedReviewsByProductId(String productId) {
        log.info("Getting featured reviews for product: {}", productId);
        return reviewRepository.findByProductIdAndFeaturedAndStatusAndDeletedFalseOrderByCreatedAtDesc(productId, true, "APPROVED")
                .stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    @Override
    public Page<ReviewDTO> getPendingReviews(Pageable pageable) {
        log.info("Getting pending reviews");
        return reviewRepository.findByStatusAndDeletedFalseOrderByCreatedAtDesc("PENDING", pageable)
                .map(reviewMapper::toDTO);
    }

    @Override
    public double calculateAverageRating(String productId) {
        log.info("Calculating average rating for product: {}", productId);
        List<Review> reviews = reviewRepository.findByProductIdAndStatusAndDeletedFalse(productId, "APPROVED");
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    @Override
    public int getReviewCount(String productId) {
        log.info("Getting review count for product: {}", productId);
        return reviewRepository.findByProductIdAndStatusAndDeletedFalse(productId, "APPROVED").size();
    }

    @Override
    public int getVerifiedReviewCount(String productId) {
        log.info("Getting verified review count for product: {}", productId);
        return reviewRepository.findByProductIdAndVerifiedPurchaseAndStatusAndDeletedFalseOrderByCreatedAtDesc(productId, true, "APPROVED", null)
                .getContent()
                .size();
    }
} 