package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

@Builder
public record LoginUserRequest(String email, String password) { }
