package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CancelOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.CancelOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderCancelStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adminOrderAdaptor", url = "${eureka.gateway}/orders", configuration = FeignClientConfig.class)
public interface AdminOrderAdaptor {

    @GetMapping("/admin")
    ResponseEntity<Page<ReadAllUserOrderStatusResponse>> getAllUserOrderStatusByPaging(Pageable pageable,
        @RequestParam(required = false) String role);

    @PutMapping("/admin/{orderId}")
    ResponseEntity<Void> updateOrderStatusByOrderId(@PathVariable String orderId,
        @RequestBody UpdateOrderStatusRequest updateOrderStatusRequests);

    @GetMapping("/admin/refund")
    ResponseEntity<Page<ReadAllUserOrderCancelStatusResponse>> getAllUserOrderCancelStatusByPaging(Pageable pageable);

    @PutMapping("/admin/{orderId}/refund")
    ResponseEntity<CancelOrderResponse> cancelOrderByOrderId(@PathVariable String orderId, @RequestBody CancelOrderRequest request);
}
