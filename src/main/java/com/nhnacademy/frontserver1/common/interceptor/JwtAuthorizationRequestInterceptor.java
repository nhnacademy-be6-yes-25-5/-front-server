package com.nhnacademy.frontserver1.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * JWT 인증을 위한 Feign 요청 인터셉터입니다.
 * 이 인터셉터는 쿠키에서 JWT 토큰을 추출하여 Feign 요청의 Authorization 헤더에 추가합니다.
 */
@Component
public class JwtAuthorizationRequestInterceptor implements RequestInterceptor {

    /**
     * Feign 요청에 JWT 토큰을 추가합니다.
     * '/auth/login' 경로에 대한 요청은 인증이 필요하지 않으므로 토큰을 추가하지 않습니다.
     *
     * @param template Feign 요청 템플릿
     */
    @Override
    public void apply(RequestTemplate template) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getServletPath();

        if (path.startsWith("/auth/login")) {
            return;
        }

        Optional<String> token = getTokenFromCookie(request);
        token.ifPresent(s -> template.header(HttpHeaders.AUTHORIZATION, "Bearer " + s));
    }

    /**
     * HttpServletRequest의 쿠키에서 'Authorization' 토큰을 추출합니다.
     *
     * @param request 현재 HTTP 요청
     * @return 쿠키에서 추출한 토큰. 토큰이 없으면 빈 Optional을 반환합니다.
     */
    private Optional<String> getTokenFromCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    return Optional.of(cookie.getValue());
                }
            }
        }

        return Optional.empty();
    }

}