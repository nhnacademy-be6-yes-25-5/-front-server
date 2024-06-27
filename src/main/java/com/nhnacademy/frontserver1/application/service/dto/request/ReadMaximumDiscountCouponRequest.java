package com.nhnacademy.frontserver1.application.service.dto.request;

import lombok.Builder;

@Builder
public record ReadMaximumDiscountCouponRequest(Integer totalAmount) {

    public static ReadMaximumDiscountCouponRequest from(Integer totalAmount) {
        return ReadMaximumDiscountCouponRequest.builder()
            .totalAmount(totalAmount)
            .build();
    }
}
