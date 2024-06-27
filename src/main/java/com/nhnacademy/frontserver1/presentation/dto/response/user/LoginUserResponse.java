package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

@Builder
public record LoginUserResponse(Long userId, String userRole, String loginStatusName){ }
