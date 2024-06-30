package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.UpdateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMyOrderHistoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.UpdateOrderResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderAdaptor", url = "${eureka.gateway}/orders", configuration = FeignClientConfig.class)
public interface OrderAdaptor {

    @PostMapping
    CreateOrderResponse createPreOrder(@RequestBody CreateOrderRequest request);

    @GetMapping("/{orderId}")
    List<ReadPaymentOrderResponse> findAllByOrderId(@PathVariable String orderId);

    @GetMapping("/status/{orderId}")
    ReadOrderStatusResponse findOrderStatusByOrderId(@PathVariable String orderId);

    @GetMapping("/mypage")
    Page<ReadMyOrderHistoryResponse> getMyOrders(Pageable pageable);

    @PutMapping("/{orderId}")
    UpdateOrderResponse updateOrderByOrderId(@PathVariable String orderId,
        @RequestBody UpdateOrderRequest request);
}
