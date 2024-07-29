package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.PointPolicyService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PointPolicyAdaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointPolicyServiceImpl implements PointPolicyService {

    private final PointPolicyAdaptor pointPolicyAdaptor;

    @Override
    public Page<PointPolicyResponse> getPointPolicies(Pageable pageable) {
        return pointPolicyAdaptor.getPointPolicies(pageable).getBody();
    }

    @Override
    public PointPolicyResponse createPointPolicy(PointPolicyRequest pointPolicyRequest) {
        return pointPolicyAdaptor.createPointPolicy(pointPolicyRequest).getBody();
    }

    @Override
    public PointPolicyResponse updatePointPolicy(Long pointPolicyId, PointPolicyRequest pointPolicyRequest) {
        return pointPolicyAdaptor.updatePointPolicy(pointPolicyId, pointPolicyRequest).getBody();
    }

    @Override
    public void deletePointPolicy(Long pointPolicyId) {
        pointPolicyAdaptor.deletePointPolicy(pointPolicyId);
    }

    @Override
    public PointPolicyResponse getPointPolicyById(Long pointPolicyId) {
        return pointPolicyAdaptor.getPointPolicy(pointPolicyId).getBody();
    }
}
