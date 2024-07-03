package com.nhnacademy.frontserver1.common.aop;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TokenCookieInjectorAspect {

    @Around("execution(* com.nhnacademy.frontserver1.presentation.controller.*.*(..))")
    public Object injectTokenToCookie(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        // DispatcherServletContext에서 토큰 가져오기
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

            String accessToken = (String) request.getAttribute("access_token");
            String refreshToken = (String) request.getAttribute("refresh_token");

            // 쿠키에 토큰 추가
            if (accessToken != null) {
                Cookie accessTokenCookie = new Cookie("AccessToken", accessToken);
                accessTokenCookie.setHttpOnly(true);
                accessTokenCookie.setSecure(true);
                accessTokenCookie.setPath("/");
                response.addCookie(accessTokenCookie);
            }
            if (refreshToken != null) {
                Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setPath("/");
                response.addCookie(refreshTokenCookie);
            }
        }

        return result;
    }

}