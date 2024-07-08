package com.nhnacademy.frontserver1.presentation.dto.response.admin;

import com.nhnacademy.frontserver1.domain.CancelStatus;
import lombok.Builder;

@Builder
public record CancelOrderResponse(CancelStatus status) {

    public static CancelOrderResponse from(CancelStatus cancelStatus) {
        return CancelOrderResponse.builder()
            .status(cancelStatus)
            .build();
    }
}
