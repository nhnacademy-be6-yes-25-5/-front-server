package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

@Builder
public record ReadUserInfoResponse(Long gradeId, Integer points) {

    public static ReadUserInfoResponse fromTest() {
        return ReadUserInfoResponse.builder()
            .gradeId(1L)
            .points(50000)
            .build();
    }
}
