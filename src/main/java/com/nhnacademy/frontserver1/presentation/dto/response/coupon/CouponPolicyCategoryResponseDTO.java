package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CouponPolicyCategoryResponseDTO(
        Long id,
        BigDecimal categoryId,
        String categoryName,
        CouponPolicyResponseDTO couponPolicy)
{}