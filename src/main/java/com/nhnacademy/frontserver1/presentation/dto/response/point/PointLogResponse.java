package com.nhnacademy.frontserver1.presentation.dto.response.point;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PointLogResponse(BigDecimal pointCurrent, String pointLogUpdatedType,
                               BigDecimal pointLogAmount, LocalDateTime pointLogUpdatedAt) {
}