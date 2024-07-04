package com.nhnacademy.frontserver1.presentation.dto.response.order;

import lombok.Builder;

@Builder
public record ReadOrderUserInfoResponse(Long userId,
                                        String name,
                                        String email,
                                        String phoneNumber,
                                        Integer points,
                                        String role
                                        ) {

    public static ReadOrderUserInfoResponse fromTestMember() {
        return ReadOrderUserInfoResponse.builder()
            .userId(8L)
            .points(1000)
            .name("김토스")
            .email("asd@dsa.dsa")
            .phoneNumber("01012345678")
            .role("MEMBER")
            .build();
    }

    public static ReadOrderUserInfoResponse fromTestNoneMember() {
        return ReadOrderUserInfoResponse.builder()
            .userId(8L)
            .role("NONE_MEMBER")
            .build();
    }
}
