package com.nhnacademy.frontserver1.infrastructure.filter;

import com.nhnacademy.frontserver1.application.service.impl.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 URI가 /auth/login이면 필터링 건너뛰기
        if (request.getRequestURI().equals("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. 요청 URI가 /auth 하위 경로이면 인증 검사 수행
        if (request.getRequestURI().startsWith("/auth/")) {
            String authHeader = request.getHeader("Authentication");
            if (authHeader != null) {
                String token = tokenService.getToken(authHeader);
                if (token != null && !token.isEmpty()) {
                    filterChain.doFilter(request, response);
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } else {
            // 3. 그 외의 요청은 모두 통과시키기
            filterChain.doFilter(request, response);
        }
    }

}