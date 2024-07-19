package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadUserCouponResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;

@Builder
public record ReadAvailableUserCouponResponse(Long userCouponId,
                                              Date CouponExpiredAt,
                                              Long couponId,

                                              String couponName,
                                              BigDecimal couponMinAmount,
                                              BigDecimal couponDiscountAmount,
                                              Double couponDiscountRate,
                                              Long bookId,
                                              List<Long> categoryIds,
                                              Boolean isAvailable,
                                              Boolean applyCouponToAllBooks) {

    public static ReadAvailableUserCouponResponse of(ReadUserCouponResponse coupon,
        boolean isAvailable) {
        return ReadAvailableUserCouponResponse.builder()
            .userCouponId(coupon.userCouponId())
            .couponId(coupon.couponId())
            .CouponExpiredAt(coupon.CouponExpiredAt())
            .couponName(coupon.couponName())
            .couponMinAmount(coupon.couponMinAmount())
            .couponDiscountAmount(coupon.couponDiscountAmount())
            .couponDiscountRate(coupon.couponDiscountRate() != null ? coupon.couponDiscountRate().doubleValue() : null)
            .bookId(coupon.bookId())
            .categoryIds(coupon.categoryIds())
            .isAvailable(isAvailable)
            .applyCouponToAllBooks(coupon.applyCouponToAllBooks())
            .build();
    }
}