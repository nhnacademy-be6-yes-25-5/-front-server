package com.nhnacademy.frontserver1.presentation.dto.request.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CouponPolicyBookRequestDTO {

        @NotEmpty(message = "Coupon policy name cannot be null")
        private String couponPolicyName;

        private BigDecimal couponPolicyDiscountValue;

        private BigDecimal couponPolicyRate;

        @NotNull(message = "Coupon policy minimum order amount cannot be null")
        private BigDecimal couponPolicyMinOrderAmount;

        @NotNull(message = "Coupon policy maximum amount cannot be null")
        private BigDecimal couponPolicyMaxAmount;

        @NotNull(message = "Coupon policy discount type cannot be null")
        private boolean couponPolicyDiscountType;

        @NotEmpty(message = "Book name cannot be null")
        private String bookName;

        @NotNull(message = "Book ID cannot be null")
        private Long bookId;
}