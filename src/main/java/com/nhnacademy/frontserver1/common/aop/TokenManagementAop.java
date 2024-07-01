package com.nhnacademy.frontserver1.common.aop;

import java.util.Arrays;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.stream.Collectors;

@Aspect
@Configuration
@RequiredArgsConstructor
public class TokenManagementAop {

    @Around("execution(* com.nhnacademy.frontserver1.application.service.*.*(..))")
    public Object manageTokens(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = requestAttributes.getResponse();
        HttpHeaders headers = getResponseHeaders(requestAttributes);

        String newAccessToken = headers.getFirst("X-New-Access-Token");
        String newRefreshToken = headers.getFirst("X-New-Refresh-Token");

        if (newAccessToken != null) {
            Cookie accessTokenCookie = new Cookie("AccessToken", newAccessToken);
            configureTokenCookie(accessTokenCookie);
            httpServletResponse.addCookie(accessTokenCookie);
            headers.remove("X-New-Access-Token");
        }

        if (newRefreshToken != null) {
            Cookie refreshTokenCookie = new Cookie("RefreshToken", newRefreshToken);
            configureTokenCookie(refreshTokenCookie);
            httpServletResponse.addCookie(refreshTokenCookie);
            headers.remove("X-New-Refresh-Token");
        }

        return result;
    }

    private void configureTokenCookie(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
    }

    private HttpHeaders getResponseHeaders(ServletRequestAttributes requestAttributes) {
        HttpServletResponse response = requestAttributes.getResponse();
        HttpHeaders headers = new HttpHeaders();
        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, Arrays.asList(response.getHeader(headerName).split(",")));
        }
        return headers;
    }
}