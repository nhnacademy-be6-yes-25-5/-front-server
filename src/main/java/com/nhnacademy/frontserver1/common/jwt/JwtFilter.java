package com.nhnacademy.frontserver1.common.jwt;

import com.nhnacademy.frontserver1.common.exception.JwtException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;
    private final CookieTokenProvider cookieTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();

        if (path.startsWith("/auth/login") || path.startsWith("/users") || path.equals("/")) {
            return;
        }

        List<String> tokens = cookieTokenProvider.getTokenFromCookie(request);
        String accessToken = tokens.get(0);
        String refreshToken = tokens.get(1);

        try {
            if (jwtProvider.isValidToken(accessToken)) {
                setAuthentication(accessToken);
            }
        } catch (JwtException e) {
            try {
                CreateAccessTokenResponse newTokens = jwtProvider.updateRefreshAccessToken(refreshToken);
                cookieTokenProvider.addAccessTokenToCookie(response, newTokens.accessToken());
                cookieTokenProvider.addRefreshTokenToCookie(response, newTokens.refreshToken());
                setAuthentication(newTokens.accessToken());
            } catch (JwtException refreshException) {
                SecurityContextHolder.clearContext();
                response.sendRedirect("/auth/login");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthentication(String token) {
        Long customerId = jwtProvider.getUserNameFromToken(token);
        String role = jwtProvider.getRolesFromToken(token);
        JwtUserDetails jwtUserDetails = JwtUserDetails.of(customerId, role);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                jwtUserDetails, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getAccessToken(HttpServletRequest request) {

        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }

        throw new JwtException(
                ErrorStatus.toErrorStatus("헤더에서 토큰을 찾을 수 없습니다.", 401, LocalDateTime.now())
        );
    }

}