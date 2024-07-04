package com.nhnacademy.frontserver1.common.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpHeaders headers = getResponseHeaders(response);

        List<String> newAccessTokens = headers.get("X-New-Access-Token");
        List<String> newRefreshTokens = headers.get("X-New-Refresh-Token");

        if (newAccessTokens != null && !newAccessTokens.isEmpty()) {
            String newAccessToken = newAccessTokens.get(0);
            Cookie accessTokenCookie = new Cookie("AccessToken", newAccessToken);
            configureTokenCookie(accessTokenCookie);
            response.addCookie(accessTokenCookie);
        }

        if (newRefreshTokens != null && !newRefreshTokens.isEmpty()) {
            String newRefreshToken = newRefreshTokens.get(0);
            Cookie refreshTokenCookie = new Cookie("RefreshToken", newRefreshToken);
            configureTokenCookie(refreshTokenCookie);
            response.addCookie(refreshTokenCookie);
        }
    }

    private void configureTokenCookie(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
    }

    private HttpHeaders getResponseHeaders(HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, Arrays.asList(response.getHeader(headerName).split(",")));
        }
        return headers;
    }
}