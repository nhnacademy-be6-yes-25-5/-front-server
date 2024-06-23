package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.application.service.dto.request.CreatePaymentsRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//@FeignClient(name = "paymentAdaptor", url = "http://133.186.153.195:8085/payments")
@FeignClient(name = "paymentAdaptor", url = "http://localhost:8085/payments")
public interface PaymentAdaptor {

    @PostMapping("/confirm")
    CreatePaymentResponse createPayment(@RequestBody CreatePaymentsRequest request);
}
