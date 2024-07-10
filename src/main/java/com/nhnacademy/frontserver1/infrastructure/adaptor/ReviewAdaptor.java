package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.common.jwt.annotation.SkipTokenInjection;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewRatingResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadReviewResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "reviewAdaptor", url = "${eureka.gateway}/reviews", configuration = FeignClientConfig.class)
public interface ReviewAdaptor {

    @PostMapping(consumes = "multipart/form-data")
    void createReview(@RequestPart("createReviewRequest") String createReviewRequestJson, @RequestPart(value = "images", required = false) List<MultipartFile> images);

    @SkipTokenInjection
    @GetMapping("/books/{bookId}")
    ResponseEntity<Page<ReadReviewResponse>>  getReviewByPaging(@PathVariable Long bookId, Pageable pageable);

    @GetMapping("/books/{bookId}/ratings")
    ResponseEntity<List<ReadReviewRatingResponse>> getReviewRatings(@PathVariable Long bookId);

    @PutMapping(value = "/{reviewId}", consumes = "multipart/form-data")
    ResponseEntity<Void> updateReviewByReviewId(@RequestPart String requestJson,
        @RequestPart(required = false) List<MultipartFile> images, @PathVariable Long reviewId);

    @DeleteMapping("/{reviewId}")
    void deleteReviewByReviewId(@PathVariable Long reviewId);
}
