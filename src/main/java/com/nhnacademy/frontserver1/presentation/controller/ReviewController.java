package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.ReviewService;
import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> createReview(@RequestPart("review") CreateReviewRequest createReviewRequest,
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        UriComponentsBuilder uriComponentsBuilder) {
        reviewService.createReview(createReviewRequest, images);

        URI location = uriComponentsBuilder.path("/detail/{bookId}")
            .buildAndExpand(createReviewRequest.bookId())
            .toUri();

        return ResponseEntity.created(location)
            .build();
    }
}
