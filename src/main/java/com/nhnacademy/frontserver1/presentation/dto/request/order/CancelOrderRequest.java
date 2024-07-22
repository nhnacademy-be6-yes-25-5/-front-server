package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.domain.CancelStatus;
import com.nhnacademy.frontserver1.domain.PaymentProvider;

public record CancelOrderRequest(CancelStatus status, PaymentProvider paymentProvider) {

}
