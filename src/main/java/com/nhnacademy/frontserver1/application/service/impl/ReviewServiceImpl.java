package com.nhnacademy.frontserver1.application.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.ReviewService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.ReviewAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewAdaptor reviewAdaptor;
    private final ObjectMapper objectMapper;

    @Override
    public void createReview(CreateReviewRequest createReviewRequest, List<MultipartFile> images) {
        try {
            String requestJson = objectMapper.writeValueAsString(createReviewRequest);
            reviewAdaptor.createReview(requestJson, images);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
