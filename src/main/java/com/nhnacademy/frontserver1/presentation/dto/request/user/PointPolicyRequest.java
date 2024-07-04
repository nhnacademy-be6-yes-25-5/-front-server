package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

import java.math.BigDecimal;

@Builder

public record PointPolicyRequest(String pointPolicyName, BigDecimal pointPolicyApply,
                                 String pointPolicyCondition, boolean pointPolicyApplyType,
                                 BigDecimal pointPolicyConditionAmount) {
}