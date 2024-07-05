package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Builder
public record CouponBoxResponse(Long userCouponId, LocalDate userCouponUsedAt, String userCouponStatus,
                                String userCouponType, Date CouponExpiredAt, Long couponId, Long userId,

                                String couponName, BigDecimal couponMinAmount, BigDecimal couponMaxAmount,
                                BigDecimal couponDiscountAmount, BigDecimal couponDiscountRate, Date couponCreatedAt,
                                String couponCode) {
}
