package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderAdaptor", url = "${eureka.gateway}/orders")
public interface OrderAdaptor {

    @PostMapping
    CreateOrderResponse createPreOrder(@RequestBody CreateOrderRequest request);
}
