package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderAdaptor", url = "${eureka.gateway}/orders")
public interface OrderAdaptor {

    @PostMapping
    CreateOrderResponse createPreOrder(@RequestBody CreateOrderRequest request);

    @GetMapping("/{orderId}")
    List<ReadPaymentOrderResponse> findAllByOrderId(@PathVariable String orderId);

    @GetMapping("/status/{orderId}")
    ReadOrderStatusResponse findOrderStatusByOrderId(@PathVariable String orderId);
}
