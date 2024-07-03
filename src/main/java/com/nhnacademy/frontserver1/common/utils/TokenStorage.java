package com.nhnacademy.frontserver1.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class TokenStorage {
    private String accessToken;
    private String refreshToken;

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public void setRefreshToken(String token) {
        this.refreshToken = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}