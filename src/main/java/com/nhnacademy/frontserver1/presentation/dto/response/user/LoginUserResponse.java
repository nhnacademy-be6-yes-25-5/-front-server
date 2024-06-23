package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

@Builder
public record LoginUserResponse(String email, String password, String userRole, String loginStatusName){ }
