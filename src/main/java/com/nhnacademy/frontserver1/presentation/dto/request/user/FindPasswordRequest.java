package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

@Builder
public record FindPasswordRequest(String email, String name) {
}
