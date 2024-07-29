package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "pointPolicyAdaptor", url = "${eureka.gateway}/users")
public interface PointPolicyAdaptor {

    @GetMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> getPointPolicy(@PathVariable Long pointPolicyId);

    @GetMapping("/admin/point-policies")
    ResponseEntity<Page<PointPolicyResponse>> getPointPolicies(Pageable pageable);

    @PostMapping("/admin/point-policies")
    ResponseEntity<PointPolicyResponse> createPointPolicy(@RequestBody PointPolicyRequest pointPolicyRequest);

    @PutMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> updatePointPolicy(@PathVariable Long pointPolicyId, @RequestBody PointPolicyRequest pointPolicyRequest);

    @DeleteMapping("/admin/point-policies/{pointPolicyId}")
    void deletePointPolicy(@PathVariable Long pointPolicyId);

    @GetMapping("/admin/point-policies/{pointPolicyId}")
    ResponseEntity<PointPolicyResponse> findPointPolicyById(@PathVariable Long pointPolicyId);
}