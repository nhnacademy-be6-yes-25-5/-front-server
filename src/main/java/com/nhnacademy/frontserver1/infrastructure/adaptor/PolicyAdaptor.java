package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "policyAdaptor", url = "${eureka.gateway}/policies", configuration = FeignClientConfig.class)
public interface PolicyAdaptor {

    @GetMapping("/shipping")
    ResponseEntity<Page<ReadShippingPolicyResponse>> findAllDeliveryPolicy(Pageable pageable);

    @GetMapping("/shipping/free")
    ResponseEntity<ReadShippingPolicyResponse> findFreePolicy();

    @GetMapping("/takeout")
    ResponseEntity<List<ReadTakeoutResponse>> findAllTakeoutPolicy();
}
