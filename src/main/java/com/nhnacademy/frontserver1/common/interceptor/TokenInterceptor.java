package com.nhnacademy.frontserver1.common.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import com.nhnacademy.frontserver1.common.context.TokenContext;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final CookieTokenProvider cookieTokenProvider;

    @Value("${cookie.set-secure}")
    private static boolean IS_SECURE;

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

        if (modelAndView != null && !"/logout".equals(request.getRequestURI())) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("AccessToken")) {
                        modelAndView.addObject("AccessToken", cookie.getValue());
                    }
                    if (cookie.getName().equals("RefreshToken")) {
                        modelAndView.addObject("RefreshToken", cookie.getValue());
                    }
                }
            }

            // 리다이렉트인 경우 URL 파라미터로 전달되지 않도록 처리
            if (modelAndView.getViewName() != null && !modelAndView.getViewName().isEmpty()) {
                if (modelAndView.getViewName().startsWith("redirect:")) {
                    Map<String, Object> flashAttributes = new HashMap<>();
                    flashAttributes.put("AccessToken", modelAndView.getModel().get("AccessToken"));
                    flashAttributes.put("RefreshToken", modelAndView.getModel().get("RefreshToken"));

                    // cartItemCount를 flashAttributes에 추가
                    if (modelAndView.getModel().containsKey("cartItemCount")) {
                        flashAttributes.put("cartItemCount", modelAndView.getModel().get("cartItemCount"));
                    }

                    modelAndView.getModel().remove("AccessToken");
                    modelAndView.getModel().remove("RefreshToken");
                    modelAndView.getModel().remove("cartItemCount");  // cartItemCount 제거

                    modelAndView.addObject("flashAttributes", flashAttributes);

                    // URL에서 cartItemCount 파라미터 제거
                    String viewName = modelAndView.getViewName();
                    if (viewName.contains("cartItemCount")) {
                        viewName = viewName.replaceAll("[?&]cartItemCount=[^&]*", "");
                        if (viewName.endsWith("?")) {
                            viewName = viewName.substring(0, viewName.length() - 1);
                        }
                        modelAndView.setViewName(viewName);
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
            cookie.setSecure(IS_SECURE);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}