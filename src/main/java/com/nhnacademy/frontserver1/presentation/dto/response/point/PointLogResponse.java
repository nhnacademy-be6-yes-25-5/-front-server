package com.nhnacademy.frontserver1.presentation.dto.response.point;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class PointLogResponse {
    private Long pointLogId;
    private LocalDateTime pointLogUpdatedAt;
    private String pointLogUsed;
    private String pointLogUpdatedType;
    private BigDecimal pointLogAmount;
}