package com.nhnacademy.frontserver1.presentation.dto.response.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record DeleteAccessTokenResponse(
        @JsonProperty("rtn_data") String loginStatus,
        String state,
        @JsonProperty("rtn_msg") String message,
        @JsonProperty("rtn_cd") String code
) {}