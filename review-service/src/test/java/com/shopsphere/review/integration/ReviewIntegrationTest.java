package com.shopsphere.review.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.model.Review;
import com.shopsphere.review.repository.ReviewRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ReviewIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
    }

    @Test
    void createReview_Success() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setUserId("user1");
        reviewDTO.setProductId("product1");
        reviewDTO.setOrderId("order1");
        reviewDTO.setOrderItemId("orderItem1");
        reviewDTO.setRating(5);
        reviewDTO.setTitle("Great product!");
        reviewDTO.setContent("This product exceeded my expectations.");
        reviewDTO.setVerifiedPurchase(true);

        mockMvc.perform(post("/api/v1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.productId").value("product1"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void getReviewById_Success() throws Exception {
        Review review = new Review();
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("APPROVED");
        review = reviewRepository.save(review);

        mockMvc.perform(get("/api/v1/reviews/{id}", review.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.productId").value("product1"))
                .andExpect(jsonPath("$.rating").value(5));
    }

    @Test
    void getReviewById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/reviews/{id}", "nonexistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getReviewsByProductId_Success() throws Exception {
        Review review = new Review();
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("APPROVED");
        reviewRepository.save(review);

        mockMvc.perform(get("/api/v1/reviews/product/{productId}", "product1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].userId").value("user1"))
                .andExpect(jsonPath("$.content[0].productId").value("product1"))
                .andExpect(jsonPath("$.content[0].rating").value(5));
    }

    @Test
    void approveReview_Success() throws Exception {
        Review review = new Review();
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("PENDING");
        review = reviewRepository.save(review);

        mockMvc.perform(put("/api/v1/reviews/{id}/approve", review.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Approved by moderator\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.moderatorNotes").value("Approved by moderator"));
    }

    @Test
    void rejectReview_Success() throws Exception {
        Review review = new Review();
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("PENDING");
        review = reviewRepository.save(review);

        mockMvc.perform(put("/api/v1/reviews/{id}/reject", review.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("\"Inappropriate content\""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"))
                .andExpect(jsonPath("$.rejectionReason").value("Inappropriate content"));
    }

    @Test
    void markAsHelpful_Success() throws Exception {
        Review review = new Review();
        review.setUserId("user1");
        review.setProductId("product1");
        review.setOrderId("order1");
        review.setOrderItemId("orderItem1");
        review.setRating(5);
        review.setTitle("Great product!");
        review.setContent("This product exceeded my expectations.");
        review.setVerifiedPurchase(true);
        review.setStatus("APPROVED");
        review = reviewRepository.save(review);

        mockMvc.perform(put("/api/v1/reviews/{id}/helpful", review.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.helpful").value(true))
                .andExpect(jsonPath("$.helpfulCount").value(1));
    }

    @Test
    void calculateAverageRating_Success() throws Exception {
        Review review1 = new Review();
        review1.setUserId("user1");
        review1.setProductId("product1");
        review1.setOrderId("order1");
        review1.setOrderItemId("orderItem1");
        review1.setRating(5);
        review1.setTitle("Great product!");
        review1.setContent("This product exceeded my expectations.");
        review1.setVerifiedPurchase(true);
        review1.setStatus("APPROVED");
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setUserId("user2");
        review2.setProductId("product1");
        review2.setOrderId("order2");
        review2.setOrderItemId("orderItem2");
        review2.setRating(4);
        review2.setTitle("Good product!");
        review2.setContent("This product met my expectations.");
        review2.setVerifiedPurchase(true);
        review2.setStatus("APPROVED");
        reviewRepository.save(review2);

        mockMvc.perform(get("/api/v1/reviews/product/{productId}/average-rating", "product1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(4.5));
    }
} 