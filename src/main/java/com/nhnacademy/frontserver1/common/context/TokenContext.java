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
        if (accessToken.get() == null) {
            return "";
        }
        return accessToken.get();
    }

    public static void setRefreshToken(String token) {
        refreshToken.set(token);
    }

    public static String getRefreshToken() {
        if (refreshToken.get() == null) {
            return "";
        }
        return refreshToken.get();
    }

    public static void clear() {
        accessToken.remove();
        refreshToken.remove();
    }
}