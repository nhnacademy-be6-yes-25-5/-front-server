package com.nhnacademy.frontserver1.presentation.dto.request.payment;

import com.nhnacademy.frontserver1.domain.PaymentProvider;

public record CreatePaymentRequest(String paymentKey, String orderId, Integer amount, PaymentProvider paymentProvider) {

}
