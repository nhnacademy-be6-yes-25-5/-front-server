package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "pointAdaptor", url = "http://localhost:8085/point")
public interface PointAdaptor {

    @GetMapping("/points/logs/{userId}")
    List<PointLogResponse> getPointLogsByUserId(@PathVariable("userId") Long userId);


}








/*
*
@FeignClient(name = "paymentAdaptor", url = "http://localhost:8085/payments")
public interface PaymentAdaptor {

    @PostMapping("/confirm")
    CreatePaymentResponse createPayment(@RequestBody CreatePaymentsRequest request);
}

* */