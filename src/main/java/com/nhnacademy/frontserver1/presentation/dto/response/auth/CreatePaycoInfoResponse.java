package com.nhnacademy.frontserver1.presentation.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreatePaycoInfoResponse(
        Header header,
        Data data
) {
    public record Header(
            @JsonProperty("isSuccessful") boolean successful,
            @JsonProperty("resultCode") int resultCode,
            @JsonProperty("resultMessage") String resultMessage
    ) {}

    public record Data(
            Member member
    ) {}

    public record Member(
            @JsonProperty("idNo") String id,
            String email,
            String mobile,
            @JsonProperty("maskedEmail") String maskedEmail,
            @JsonProperty("maskedMobile") String maskedMobile,
            String name,
            @JsonProperty("genderCode") String genderCode,
            @JsonProperty("birthdayMMdd") String birthdayMMdd
    ) {}
}