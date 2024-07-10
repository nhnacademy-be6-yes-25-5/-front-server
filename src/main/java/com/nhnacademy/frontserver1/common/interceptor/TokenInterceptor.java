package com.nhnacademy.frontserver1.common.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import com.nhnacademy.frontserver1.common.context.TokenContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final CookieTokenProvider cookieTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<String> tokens = cookieTokenProvider.getTokenFromCookie(request);

        String accessToken = tokens.get(0);
        String refreshToken = tokens.get(1);

        TokenContext.setAccessToken(accessToken);
        TokenContext.setRefreshToken(refreshToken);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        String accessToken = TokenContext.getAccessToken();
        String refreshToken = TokenContext.getRefreshToken();

        assert accessToken != null;
        if (!accessToken.isEmpty()) {
            assert refreshToken != null;
            if (!refreshToken.isEmpty()) {
                addTokenCookie(response, "AccessToken", accessToken);
                addTokenCookie(response, "RefreshToken", refreshToken);
            }
        }

        if (modelAndView != null) {
            // 현재 요청이 /logout이 아니고, 리다이렉트가 아닌 경우에만 처리
            if (!"/logout".equals(request.getRequestURI())) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("AccessToken")) {
                            String accessCookie = cookie.getValue();
                            modelAndView.addObject("AccessToken", accessCookie);
                        }
                        if (cookie.getName().equals("RefreshToken")) {
                            String refreshCookie = cookie.getValue();
                            modelAndView.addObject("RefreshToken", refreshCookie);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TokenContext.clear();
    }

    private void addTokenCookie(HttpServletResponse response, String name, String token) {
        if (token != null) {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(true);
            //배포시에는 아래 주석 풀기\
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}