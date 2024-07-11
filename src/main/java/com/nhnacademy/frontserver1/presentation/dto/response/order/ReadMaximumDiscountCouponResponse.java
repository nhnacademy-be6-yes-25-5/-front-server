package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReadMaximumDiscountCouponResponse(Long couponId,
                                                BigDecimal discountAmount,
                                                String couponName) {
}
