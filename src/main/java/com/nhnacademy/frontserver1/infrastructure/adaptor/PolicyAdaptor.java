package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.ApiResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "policyAdaptor", url = "http://localhost:8081/policies")
public interface PolicyAdaptor {

    @GetMapping("/shipping")
    ApiResponse<Page<ReadShippingPolicyResponse>> findAllDeliveryPolicy(Pageable pageable);

    @GetMapping("/shipping/free")
    ApiResponse<ReadShippingPolicyResponse> findFreePolicy();
}
