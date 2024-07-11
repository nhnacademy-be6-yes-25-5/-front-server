package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

@Builder
public record UpdatePasswordRequest(String userPassword, String confirmPassword) {}