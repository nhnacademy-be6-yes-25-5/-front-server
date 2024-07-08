package com.nhnacademy.frontserver1.presentation.dto.request.review;

public record CreateReviewRequest(String name, String subject, Integer rating, String message, Long bookId) {

}
