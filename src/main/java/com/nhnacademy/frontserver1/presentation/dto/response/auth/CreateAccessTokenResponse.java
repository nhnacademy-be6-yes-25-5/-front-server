package com.nhnacademy.frontserver1.presentation.dto.response.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateAccessTokenResponse(
        @JsonProperty("access_token_secret") String accessTokenSecret,
        String state,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") String expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("access_token") String accessToken
) {}