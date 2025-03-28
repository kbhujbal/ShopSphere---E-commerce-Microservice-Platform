package com.shopsphere.review.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.shopsphere.common.repository.BaseRepository;
import com.shopsphere.review.model.Review;

@Repository
public interface ReviewRepository extends BaseRepository<Review> {
    Page<Review> findByProductIdAndStatusAndDeletedFalseOrderByCreatedAtDesc(String productId, String status, Pageable pageable);
    Page<Review> findByUserIdAndDeletedFalseOrderByCreatedAtDesc(String userId, Pageable pageable);
    Page<Review> findByProductIdAndVerifiedPurchaseAndStatusAndDeletedFalseOrderByCreatedAtDesc(String productId, boolean verifiedPurchase, String status, Pageable pageable);
    Optional<Review> findByOrderItemIdAndDeletedFalse(String orderItemId);
    List<Review> findByProductIdAndStatusAndDeletedFalse(String productId, String status);
    List<Review> findByProductIdAndFeaturedAndStatusAndDeletedFalseOrderByCreatedAtDesc(String productId, boolean featured, String status);
    Page<Review> findByStatusAndDeletedFalseOrderByCreatedAtDesc(String status, Pageable pageable);
} 