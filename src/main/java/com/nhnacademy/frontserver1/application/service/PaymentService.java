package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {

    CreatePaymentResponse createPayment(@RequestBody CreatePaymentRequest request);
}
