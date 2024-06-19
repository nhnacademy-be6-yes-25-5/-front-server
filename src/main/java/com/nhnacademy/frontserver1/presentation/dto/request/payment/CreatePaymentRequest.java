package com.nhnacademy.frontserver1.presentation.dto.request.payment;

public record CreatePaymentRequest(String paymentKey, String orderId, Integer amount) {

}
