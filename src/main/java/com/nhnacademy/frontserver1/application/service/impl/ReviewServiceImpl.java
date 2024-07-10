package com.nhnacademy.frontserver1.application.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.ReviewService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.ReviewAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.review.UpdateReviewRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewRatingResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewAdaptor reviewAdaptor;
    private final ObjectMapper objectMapper;

    @Override
    public void createReview(CreateReviewRequest createReviewRequest, List<MultipartFile> images) {
        String requestJson = getJsonFrom(createReviewRequest);
        reviewAdaptor.createReview(requestJson, images);
    }

    @Override
    public Page<ReadReviewResponse> getReviews(Long bookId, Pageable pageable) {
        return reviewAdaptor.getReviewByPaging(bookId, pageable).getBody();
    }

    @Override
    public List<ReadReviewRatingResponse> getReviewRatings(Long bookId) {
        return reviewAdaptor.getReviewRatings(bookId).getBody();
    }

    @Override
    public void updateReview(UpdateReviewRequest updateReviewRequest, List<MultipartFile> images,
        Long reviewId) {
        String requestJson = getJsonFrom(updateReviewRequest);
        reviewAdaptor.updateReviewByReviewId(requestJson, images, reviewId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewAdaptor.deleteReviewByReviewId(reviewId);
    }

    private <T> String getJsonFrom(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
