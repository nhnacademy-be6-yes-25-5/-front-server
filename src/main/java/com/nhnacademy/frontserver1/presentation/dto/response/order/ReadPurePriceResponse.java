package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReadPurePriceResponse(BigDecimal purePrice) {

    public static ReadPurePriceResponse fromTest() {
        return ReadPurePriceResponse.builder()
            .purePrice(BigDecimal.valueOf(80000))
            .build();
    }
}
