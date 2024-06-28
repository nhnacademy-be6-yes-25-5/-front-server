package com.nhnacademy.frontserver1.common.provider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CookieTokenProvider {

    @Value("${jwt.access-token.cookie-name}")
    private String accessTokenCookieName;

    @Value("${jwt.refresh-token.cookie-name}")
    private String refreshTokenCookieName;

    @Value("${jwt.access-token.expiration-ms}")
    private int accessTokenExpiration;

    @Value("${jwt.refresh-token.expiration-ms}")
    private int refreshTokenExpiration;

    public List<String> getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return List.of("", "");
        }

        String accessToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(accessTokenCookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(refreshTokenCookieName))
                .map(Cookie::getValue)
                .findFirst()
                .orElse("");

        return List.of(accessToken, refreshToken);
    }

    public void addAccessTokenToCookie(HttpServletResponse response, String accessToken) {
        addTokenToCookie(response, accessTokenCookieName, accessToken, accessTokenExpiration);
    }

    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        addTokenToCookie(response, refreshTokenCookieName, refreshToken, refreshTokenExpiration);
    }

    private void addTokenToCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}