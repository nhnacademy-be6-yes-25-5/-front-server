package com.nhnacademy.frontserver1.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentProvider {
    TOSS,
    NAVER,
    KAKAO;

    @JsonCreator
    public static PaymentProvider from(String value) {
        return PaymentProvider.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name().toLowerCase();
    }
}
