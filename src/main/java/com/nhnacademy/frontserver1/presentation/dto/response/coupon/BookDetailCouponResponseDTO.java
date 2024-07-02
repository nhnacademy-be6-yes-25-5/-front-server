package com.nhnacademy.frontserver1.presentation.dto.response.coupon;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class BookDetailCouponResponseDTO {
    private final String couponName;
    private final Date couponExpiredAt;
    private final String couponPolicyName;
    private final BigDecimal couponPolicyDiscountValue;
    private final BigDecimal couponPolicyRate;
}