package com.nhnacademy.frontserver1.common.interceptor;

import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.util.StringUtils;

/**
 * JWT 인증을 위한 Feign 요청 인터셉터입니다.
 * 이 인터셉터는 쿠키에서 JWT 토큰을 추출하여 Feign 요청의 Authorization 헤더에 추가합니다.
 * Authorization 쿠키(토큰이 담겨있는 쿠키)가 없는 경우 NoTokenCookieException을 던져 로그인 페이지로 리디렉션합니다.
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationRequestInterceptor implements RequestInterceptor {

    private final CookieTokenProvider cookieTokenProvider;
//
//    @Value("${app.mode}")
//    private String mode;

    /**
     * Feign 요청에 JWT 토큰을 추가합니다.
     * '/auth/login' 경로에 대한 요청은 인증이 필요하지 않으므로 토큰을 추가하지 않습니다.
     * @param template Feign 요청 템플릿
     */
    @Override
    public void apply(RequestTemplate template) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getServletPath();

        if (path.startsWith("/auth/login") || path.startsWith("/orders/none") || path.matches(".*/orders/.*/delivery.*")
                || path.startsWith("/users/sign-up") || path.equals("/coupon/modal") || path.matches("/coupons/books/\\d+/coupons")) {
            return;
        }

        Optional<String> token = cookieTokenProvider.getTokenFromCookie(request);
        if (token.isEmpty() && path.matches(".*/orders/.*/delivery.*")) {
            return ;
        }

        token.ifPresentOrElse(
            t -> {
                log.debug("Adding Authorization header: Bearer {}", t);
                template.header(HttpHeaders.AUTHORIZATION, "Bearer " + t);
            },
            () -> {
                log.warn("Authorization token is missing in the cookies.");
                throw new TokenCookieMissingException();
            }
        );
    }

}