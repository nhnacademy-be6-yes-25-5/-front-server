package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import java.math.BigDecimal;
import java.util.Date;

public record CouponUserListResponseDTO(
        String couponName,
        String couponCode,
        Long couponPolicyDiscountValue,
        Integer couponPolicyRate,
        BigDecimal couponPolicyMinOrderAmount,
        BigDecimal couponPolicyMaxAmount,
        Date couponCreatedAt,
        Date couponExpiredAt
) {}
