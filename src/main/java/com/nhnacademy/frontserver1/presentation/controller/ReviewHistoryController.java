package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.ReviewService;
import com.nhnacademy.frontserver1.presentation.dto.response.review.ReadMyReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class ReviewHistoryController {

    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public String getMyReviewPage(Model model, Pageable pageable) {
        Page<ReadMyReviewResponse> responses = reviewService.getMyReviews(pageable);
        model.addAttribute("reviews", responses);

        return "mypage/mypage-review";
    }
}
