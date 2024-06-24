package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import java.math.BigDecimal;
import java.util.Date;

public record CouponUserListResponseDTO(
        Long userCouponId,
        Long userId,
        Long couponId,
        String couponName,
        String couponCode,
        BigDecimal couponPolicyDiscountValue,
        BigDecimal couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        Date couponCreatedAt,
        Date couponExpiredAt,
        Date userCouponUsedAt
) {}