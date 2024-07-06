package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewRatingResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    void createReview(CreateReviewRequest createReviewRequest, List<MultipartFile> images);

    Page<ReadReviewResponse> getReviews(Long bookId, Pageable pageable);

    List<ReadReviewRatingResponse> getReviewRatings(Long bookId);
}
