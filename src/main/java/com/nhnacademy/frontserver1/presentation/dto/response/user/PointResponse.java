package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PointResponse(BigDecimal point) {
}
