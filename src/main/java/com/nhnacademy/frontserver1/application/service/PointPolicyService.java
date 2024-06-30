package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointPolicyService {

    Page<PointPolicyResponse> getPointPolicies(Pageable pageable);

    PointPolicyResponse createPointPolicy(PointPolicyRequest pointPolicyRequest);

    PointPolicyResponse updatePointPolicy(Long pointPolicyId, PointPolicyRequest pointPolicyRequest);

    void deletePointPolicy(Long pointPolicyId);
}
