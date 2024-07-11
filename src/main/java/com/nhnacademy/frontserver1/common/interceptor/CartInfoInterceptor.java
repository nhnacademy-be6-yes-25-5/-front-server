package com.nhnacademy.frontserver1.common.interceptor;

import com.nhnacademy.frontserver1.application.service.CartService;
import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class CartInfoInterceptor implements HandlerInterceptor {

    private final ApplicationContext applicationContext;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("AccessToken".equals(cookie.getName())) {
                        CartService cartService = applicationContext.getBean(CartService.class);
                        List<ReadCartBookResponse> cartPreview = cartService.getCarts();

                        modelAndView.addObject("cartPreview", cartPreview);
                        modelAndView.addObject("cartItemCount", cartPreview.size());

                        break;
                    }
                }
            }
        }
    }
}
