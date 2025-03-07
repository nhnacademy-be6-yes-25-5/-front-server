package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import lombok.Builder;

@Builder
public record BookCouponResponseDTO(
        Long bookId,
        String bookName,
        String authorName,
        String bookPublisher
) {
}