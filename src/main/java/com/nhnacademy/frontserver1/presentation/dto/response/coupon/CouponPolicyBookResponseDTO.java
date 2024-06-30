package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CouponPolicyBookResponseDTO(
        Long id,
        BigDecimal bookId,
        String bookName,
        CouponPolicyResponseDTO couponPolicy)
{}