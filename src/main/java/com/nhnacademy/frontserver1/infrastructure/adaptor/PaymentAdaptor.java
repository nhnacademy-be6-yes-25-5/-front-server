package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.application.service.dto.request.CreatePaymentsRequest;
import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentAdaptor", url = "${eureka.gateway}/payments", configuration = FeignClientConfig.class)
public interface PaymentAdaptor {

    @PostMapping("/confirm")
    ResponseEntity<CreatePaymentResponse> createPayment(@RequestBody CreatePaymentsRequest request);

    @GetMapping("/{orderId}")
    ResponseEntity<List<ReadPaymentOrderResponse>> findAllByOrderId(@PathVariable String orderId);

    @PostMapping("/confirm/zero")
    ResponseEntity<CreatePaymentResponse> createPaymentByZeroAmount(@RequestBody CreatePaymentsRequest paymentsRequest);
}
