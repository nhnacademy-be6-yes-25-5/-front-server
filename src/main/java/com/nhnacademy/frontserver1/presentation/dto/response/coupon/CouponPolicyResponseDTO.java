package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import java.math.BigDecimal;
import java.util.Date;

public record CouponPolicyResponseDTO(
        Long couponPolicyId,
        String couponPolicyName,
        BigDecimal couponPolicyDiscountValue,
        Date couponPolicyCreatedAt,
        Date couponPolicyUpdatedAt,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        boolean couponPolicyDiscountType
) {}
