package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReadPurePriceResponse(BigDecimal purePrice, LocalDate recordedAt) {
}
