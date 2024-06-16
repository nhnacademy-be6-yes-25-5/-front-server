package com.nhnacademy.frontserver1.presentation.dto.request.order;

public record CreatePaymentRequest(String paymentKey, String orderId, String amount) {

}
