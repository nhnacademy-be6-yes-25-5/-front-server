package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "adminOrderAdaptor", url = "${eureka.gateway}/orders", configuration = FeignClientConfig.class)
public interface AdminOrderAdaptor {

    @GetMapping("/admin")
    Page<ReadAllUserOrderStatusResponse> getAllUserOrderStatusByPaging(Pageable pageable,
        @RequestParam(required = false) String role);

    @PutMapping("/{orderId}/admin")
    void updateOrderStatusByOrderId(@PathVariable String orderId,
        @RequestBody UpdateOrderStatusRequest updateOrderStatusRequests);
}
