package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReadMaximumDiscountCouponResponse(Long couponId,
                                                BigDecimal discountAmount) {

    public static ReadMaximumDiscountCouponResponse fromTest() {
        return ReadMaximumDiscountCouponResponse.builder()
            .couponId(1L)
            .discountAmount(BigDecimal.valueOf(2000))
            .build();
    }
}
