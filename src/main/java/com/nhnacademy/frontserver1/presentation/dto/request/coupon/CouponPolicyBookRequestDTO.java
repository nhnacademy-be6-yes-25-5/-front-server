package com.nhnacademy.frontserver1.presentation.dto.request.coupon;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CouponPolicyBookRequestDTO {

        @NotNull(message = "Coupon policy ID cannot be null")
        private Long couponPolicyId;

        @NotEmpty(message = "Book name cannot be null")
        private String bookName;

        @NotNull(message = "Book ID cannot be null")
        private Long bookId;

        @NotEmpty(message = "Coupon policy name cannot be null")
        private String couponPolicyName;

        private BigDecimal couponPolicyDiscountValue;

        private BigDecimal couponPolicyRate;

        @NotNull(message = "Coupon policy minimum order amount cannot be null")
        private BigDecimal couponPolicyMinOrderAmount;

        @NotNull(message = "Coupon policy maximum amount cannot be null")
        private BigDecimal couponPolicyMaxAmount;

        private boolean couponPolicyDiscountType;
}