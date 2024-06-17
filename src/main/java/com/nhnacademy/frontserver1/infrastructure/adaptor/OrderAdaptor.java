package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.presentation.dto.request.order.request.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderAdaptor", url = "http://localhost:8081/orders")
public interface OrderAdaptor {

    @PostMapping
    CreateOrderResponse createPreOrder(@RequestBody CreateOrderRequest request,@RequestParam Long userId);
}
