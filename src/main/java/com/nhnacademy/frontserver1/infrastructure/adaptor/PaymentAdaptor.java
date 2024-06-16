package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentAdaptor", url = "http://localhost:8081/payments")
public interface PaymentAdaptor {

    @PostMapping("/confirm")
    CreatePaymentResponse createPayment(@RequestBody String request);
}
