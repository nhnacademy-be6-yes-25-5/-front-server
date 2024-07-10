package com.nhnacademy.frontserver1.common.context;

import org.springframework.stereotype.Component;

@Component
public class TokenContext {
    private static final ThreadLocal<String> accessToken = new ThreadLocal<>();
    private static final ThreadLocal<String> refreshToken = new ThreadLocal<>();

    public static void setAccessToken(String token) {
        accessToken.set(token);
    }

    public static String getAccessToken() {
        return accessToken.get();
    }

    public static void setRefreshToken(String token) {
        refreshToken.set(token);
    }

    public static String getRefreshToken() {
        return refreshToken.get();
    }

    public static void clear() {
        accessToken.remove();
        refreshToken.remove();
    }
}