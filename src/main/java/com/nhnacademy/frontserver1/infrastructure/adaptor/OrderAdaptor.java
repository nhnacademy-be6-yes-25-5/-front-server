package com.nhnacademy.frontserver1.infrastructure.adaptor;


import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.UpdateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMyOrderHistoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDeliveryInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDetailResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.UpdateOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "orderAdaptor", url = "${eureka.gateway}/orders", configuration = FeignClientConfig.class)
public interface OrderAdaptor {

    @PostMapping
    ResponseEntity<CreateOrderResponse> createPreOrder(@RequestBody CreateOrderRequest request);

    @GetMapping("/status/{orderId}")
    ResponseEntity<ReadOrderStatusResponse> findOrderStatusByOrderId(@PathVariable String orderId);

    @GetMapping("/mypage")
    ResponseEntity<Page<ReadMyOrderHistoryResponse>> getMyOrders(Pageable pageable);

    @PutMapping("/{orderId}")
    ResponseEntity<UpdateOrderResponse> updateOrderByOrderId(@PathVariable String orderId,
        @RequestBody UpdateOrderRequest request);

    @GetMapping("/{orderId}/delivery")
    ResponseEntity<ReadOrderDeliveryInfoResponse> getMyOrderDeliveryByOrderId(@PathVariable String orderId);

    @GetMapping("/{orderId}")
    ResponseEntity<ReadOrderDetailResponse> getMyOrderByOrderId(@PathVariable String orderId);

    @GetMapping("/none/{orderId}")
    ResponseEntity<ReadOrderDetailResponse> findOrderNoneMemberByOrderIdAndEmail(@PathVariable String orderId, @RequestParam String email);
}
