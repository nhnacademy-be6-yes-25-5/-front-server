package com.nhnacademy.frontserver1.common.utils;

import com.nhnacademy.frontserver1.common.context.TokenContext;
import com.nhnacademy.frontserver1.common.exception.AccessDeniedException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    private CookieUtils() {
    }

    public static void validateAccessToken(HttpServletRequest request) {
        Optional<Cookie> accessTokenCookie = getAccessTokenCookie(request);

        accessTokenCookie.ifPresent(cookie -> {
            if (TokenContext.getAccessToken() != null) {
                throw new AccessDeniedException(ErrorStatus.toErrorStatus(
                        "이미 로그인된 상태입니다.", 409, LocalDateTime.now()
                ));
            }
        });
    }

    public static Optional<String> getAccessTokenValue(HttpServletRequest request) {
        return getAccessTokenCookie(request)
                .map(Cookie::getValue);
    }

    private static Optional<Cookie> getAccessTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        return Arrays.stream(cookies)
                .filter(cookie -> "AccessToken".equals(cookie.getName()) && cookie.getValue() != null)
                .findFirst();
    }
}