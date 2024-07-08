package com.nhnacademy.frontserver1.presentation.dto.request.review;

public record UpdateReviewRequest(String name, String subject, String message, Long bookId, Integer rating) {
}
