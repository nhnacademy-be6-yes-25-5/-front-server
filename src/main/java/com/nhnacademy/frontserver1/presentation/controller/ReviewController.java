package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.ReviewService;
import com.nhnacademy.frontserver1.presentation.dto.request.review.CreateReviewRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewRatingResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/books/{bookId}")
    public String getReview(@PathVariable Long bookId, Model model, Pageable pageable) {
        Page<ReadReviewResponse> reviewResponse = reviewService.getReviews(bookId, pageable);
        List<ReadReviewRatingResponse> reviewRatings = reviewService.getReviewRatings(bookId);

        double averageRating = reviewRatings.stream()
            .mapToInt(ReadReviewRatingResponse::rating)
            .average()
            .orElse(0);

        long[] ratingCounts = new long[5];
        for (ReadReviewRatingResponse rating : reviewRatings) {
            if (rating.rating() >= 1 && rating.rating() <= 5) {
                ratingCounts[rating.rating() - 1]++;
            }
        }

        model.addAttribute("reviews", reviewResponse);
        model.addAttribute("ratings", reviewRatings);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("ratingCounts", ratingCounts);

        return "fragments/review";
    }
}
