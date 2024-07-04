package com.nhnacademy.frontserver1.common.interceptor;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {

    private final ApplicationContext applicationContext;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            UserService userService = applicationContext.getBean(UserService.class);
            ReadUserInfoResponse userInfo = userService.getUserPointsAndGrade();
            modelAndView.addObject("userInfo", userInfo);
        }
    }
}
