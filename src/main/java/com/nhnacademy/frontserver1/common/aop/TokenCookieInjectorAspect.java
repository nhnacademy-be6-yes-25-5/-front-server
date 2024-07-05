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

/**
 * 토큰을 쿠키에 주입하는 Aspect 클래스입니다.
 * 이 클래스는 컨트롤러 메소드 실행 후 토큰을 쿠키에 추가하는 역할을 합니다.
 */
@Aspect
@Component
public class TokenCookieInjectorAspect {

    /**
     * 컨트롤러 메소드 실행 전후에 토큰을 쿠키에 주입합니다.
     *
     * @param joinPoint 메소드 실행 지점
     * @return 메소드 실행 결과
     * @throws Throwable 메소드 실행 중 발생할 수 있는 예외
     */
    @Around("execution(* com.nhnacademy.frontserver1.presentation.controller.*.*(..))")
    public Object injectTokenToCookie(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

            String accessToken = (String) request.getAttribute("AccessToken");
            if (accessToken != null) {
                accessToken = accessToken.replace("Bearer ", "");
            }
            String refreshToken = (String) request.getAttribute("RefreshToken");

            addTokenCookie(response, "AccessToken", accessToken);
            addTokenCookie(response, "RefreshToken", refreshToken);
        }

        return result;
    }

    /**
     * 쿠키에 토큰을 추가합니다.
     *
     * @param response HTTP 응답 객체
     * @param name 쿠키 이름
     * @param token 토큰 값
     */
    private void addTokenCookie(HttpServletResponse response, String name, String token) {
        if (token != null) {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}