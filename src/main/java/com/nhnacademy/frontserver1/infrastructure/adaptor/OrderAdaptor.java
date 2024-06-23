package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "orderAdaptor", url = "http://133.186.153.195:8085/orders")
@FeignClient(name = "orderAdaptor", url = "http://localhost:8085/orders")
public interface OrderAdaptor {

    @PostMapping
    CreateOrderResponse createPreOrder(@RequestBody CreateOrderRequest request);
}
