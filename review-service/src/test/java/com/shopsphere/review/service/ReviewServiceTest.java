package com.shopsphere.review.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.shopsphere.common.exception.ResourceNotFoundException;
import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.mapper.ReviewMapper;
import com.shopsphere.review.model.Review;
import com.shopsphere.review.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDTO reviewDTO;
    private Review review;

    @BeforeEach
    void setUp() {
        reviewDTO = new ReviewDTO();
        reviewDTO.setUserId("user1");
        reviewDTO.setProductId("product1");
        reviewDTO.setOrderId("order1");
        reviewDTO.setOrderItemId("orderItem1");
        reviewDTO.setRating(5);
        reviewDTO.setTitle("Great product!");
        reviewDTO.setContent("This product exceeded my expectations.");
        reviewDTO.setVerifiedPurchase(true);

        review = new Review();
        review.setId("review1");
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("PENDING");
    }

    @Test
    void createReview_Success() {
        when(reviewMapper.toEntity(any(ReviewDTO.class))).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        assertEquals("product1", result.getProductId());
        assertEquals(5, result.getRating());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void updateReview_Success() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewMapper.toEntity(any(ReviewDTO.class))).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.updateReview("review1", reviewDTO);

        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        assertEquals("product1", result.getProductId());
        assertEquals(5, result.getRating());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void updateReview_NotFound() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            reviewService.updateReview("review1", reviewDTO));
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_Success() {
        when(reviewRepository.existsById("review1")).thenReturn(true);
        doNothing().when(reviewRepository).deleteById("review1");

        reviewService.deleteReview("review1");

        verify(reviewRepository).deleteById("review1");
    }

    @Test
    void deleteReview_NotFound() {
        when(reviewRepository.existsById("review1")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> 
            reviewService.deleteReview("review1"));
        verify(reviewRepository, never()).deleteById(any(String.class));
    }

    @Test
    void getReviewById_Success() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        Optional<ReviewDTO> result = reviewService.getReviewById("review1");

        assertTrue(result.isPresent());
        assertEquals("user1", result.get().getUserId());
        assertEquals("product1", result.get().getProductId());
        assertEquals(5, result.get().getRating());
    }

    @Test
    void getReviewById_NotFound() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.empty());

        Optional<ReviewDTO> result = reviewService.getReviewById("review1");

        assertFalse(result.isPresent());
        verify(reviewMapper, never()).toDTO(any(Review.class));
    }

    @Test
    void approveReview_Success() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.approveReview("review1", "Approved by moderator");

        assertNotNull(result);
        assertEquals("APPROVED", review.getStatus());
        assertNotNull(review.getApprovedAt());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void rejectReview_Success() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.rejectReview("review1", "Inappropriate content");

        assertNotNull(result);
        assertEquals("REJECTED", review.getStatus());
        assertEquals("Inappropriate content", review.getRejectionReason());
        assertNotNull(review.getRejectedAt());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void markAsHelpful_Success() {
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.markAsHelpful("review1");

        assertNotNull(result);
        assertTrue(review.isHelpful());
        assertEquals(1, review.getHelpfulCount());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void markAsNotHelpful_Success() {
        review.setHelpful(true);
        review.setHelpfulCount(1);
        when(reviewRepository.findById("review1")).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(reviewDTO);

        ReviewDTO result = reviewService.markAsNotHelpful("review1");

        assertNotNull(result);
        assertFalse(review.isHelpful());
        assertEquals(0, review.getHelpfulCount());
        verify(reviewRepository).save(any(Review.class));
    }
} 