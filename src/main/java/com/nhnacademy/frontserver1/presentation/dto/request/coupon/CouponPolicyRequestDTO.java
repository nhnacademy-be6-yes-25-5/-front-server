package com.nhnacademy.frontserver1.presentation.dto.request.coupon;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
@Data
public class CouponPolicyRequestDTO {
    private String couponPolicyName;
    private BigDecimal couponPolicyDiscountValue;
    private BigDecimal couponPolicyRate;
    private BigDecimal couponPolicyMinOrderAmount;
    private BigDecimal couponPolicyMaxAmount;
    private boolean couponPolicyDiscountType;
}