package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ReviewService {

    void createReview(CreateReviewRequest createReviewRequest, List<MultipartFile> images);
}
