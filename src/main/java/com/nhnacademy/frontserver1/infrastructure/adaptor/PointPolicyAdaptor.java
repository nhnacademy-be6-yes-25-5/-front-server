package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.user.PointPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pointPolicyAdaptor", url = "${eureka.gateway}")
public interface PointPolicyAdaptor {

    @GetMapping("/admin/point-policies/{pointPolicyId}")
    PointPolicyResponse getPointPolicy(@PathVariable Long pointPolicyId);
}