package com.nhnacademy.frontserver1.presentation.dto.response.auth;

import lombok.Builder;

@Builder
public record CreateAccessTokenResponse(String accessToken, String refreshToken) { }
