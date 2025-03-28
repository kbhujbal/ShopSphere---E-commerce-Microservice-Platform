package com.shopsphere.review.mapper;

import com.shopsphere.review.dto.ReviewDTO;
import com.shopsphere.review.model.Review;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-27T17:12:52-0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review toEntity(ReviewDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Review review = new Review();

        review.setUserId( dto.getUserId() );
        review.setProductId( dto.getProductId() );
        review.setOrderId( dto.getOrderId() );
        review.setOrderItemId( dto.getOrderItemId() );
        review.setRating( dto.getRating() );
        review.setTitle( dto.getTitle() );
        review.setContent( dto.getContent() );
        List<String> list = dto.getImages();
        if ( list != null ) {
            review.setImages( new ArrayList<String>( list ) );
        }
        review.setVerifiedPurchase( dto.isVerifiedPurchase() );
        review.setHelpful( dto.isHelpful() );
        review.setHelpfulCount( dto.getHelpfulCount() );
        review.setStatus( dto.getStatus() );
        review.setModeratorNotes( dto.getModeratorNotes() );
        review.setApprovedAt( dto.getApprovedAt() );
        review.setRejectedAt( dto.getRejectedAt() );
        review.setRejectionReason( dto.getRejectionReason() );
        review.setFeatured( dto.isFeatured() );
        review.setLikes( dto.getLikes() );
        review.setDislikes( dto.getDislikes() );
        List<String> list1 = dto.getTags();
        if ( list1 != null ) {
            review.setTags( new ArrayList<String>( list1 ) );
        }
        review.setPros( dto.getPros() );
        review.setCons( dto.getCons() );
        review.setAnonymous( dto.isAnonymous() );
        review.setUserNickname( dto.getUserNickname() );
        review.setUserLocation( dto.getUserLocation() );
        review.setPurchaseDate( dto.getPurchaseDate() );
        review.setProductVariant( dto.getProductVariant() );
        review.setProductSize( dto.getProductSize() );
        review.setProductColor( dto.getProductColor() );

        return review;
    }

    @Override
    public ReviewDTO toDTO(Review entity) {
        if ( entity == null ) {
            return null;
        }

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setId( entity.getId() );
        reviewDTO.setUserId( entity.getUserId() );
        reviewDTO.setProductId( entity.getProductId() );
        reviewDTO.setOrderId( entity.getOrderId() );
        reviewDTO.setOrderItemId( entity.getOrderItemId() );
        reviewDTO.setRating( entity.getRating() );
        reviewDTO.setTitle( entity.getTitle() );
        reviewDTO.setContent( entity.getContent() );
        List<String> list = entity.getImages();
        if ( list != null ) {
            reviewDTO.setImages( new ArrayList<String>( list ) );
        }
        reviewDTO.setVerifiedPurchase( entity.isVerifiedPurchase() );
        reviewDTO.setHelpful( entity.isHelpful() );
        reviewDTO.setHelpfulCount( entity.getHelpfulCount() );
        reviewDTO.setStatus( entity.getStatus() );
        reviewDTO.setModeratorNotes( entity.getModeratorNotes() );
        reviewDTO.setApprovedAt( entity.getApprovedAt() );
        reviewDTO.setRejectedAt( entity.getRejectedAt() );
        reviewDTO.setRejectionReason( entity.getRejectionReason() );
        reviewDTO.setFeatured( entity.isFeatured() );
        reviewDTO.setLikes( entity.getLikes() );
        reviewDTO.setDislikes( entity.getDislikes() );
        List<String> list1 = entity.getTags();
        if ( list1 != null ) {
            reviewDTO.setTags( new ArrayList<String>( list1 ) );
        }
        reviewDTO.setPros( entity.getPros() );
        reviewDTO.setCons( entity.getCons() );
        reviewDTO.setAnonymous( entity.isAnonymous() );
        reviewDTO.setUserNickname( entity.getUserNickname() );
        reviewDTO.setUserLocation( entity.getUserLocation() );
        reviewDTO.setPurchaseDate( entity.getPurchaseDate() );
        reviewDTO.setProductVariant( entity.getProductVariant() );
        reviewDTO.setProductSize( entity.getProductSize() );
        reviewDTO.setProductColor( entity.getProductColor() );

        return reviewDTO;
    }
}
