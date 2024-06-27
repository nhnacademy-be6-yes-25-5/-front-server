package com.nhnacademy.frontserver1.presentation.dto.response.order;


import lombok.Builder;

@Builder
public record SuccessPaymentResponse(String orderId) {

    public static SuccessPaymentResponse from(String orderId) {
        return SuccessPaymentResponse.builder()
            .orderId(orderId)
            .build();
    }
}
