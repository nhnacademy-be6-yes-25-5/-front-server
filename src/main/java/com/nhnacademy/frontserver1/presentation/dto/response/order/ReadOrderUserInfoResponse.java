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
}
