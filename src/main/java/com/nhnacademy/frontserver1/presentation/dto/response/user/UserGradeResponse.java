package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

@Builder
public record UserGradeResponse(Long userGradeId, String userGradeName, Long pointPolicyId, String pointPolicyCondition) {
}
