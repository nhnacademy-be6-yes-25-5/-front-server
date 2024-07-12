package com.nhnacademy.frontserver1.common.config;

import com.nhnacademy.frontserver1.common.interceptor.CartInfoInterceptor;
import com.nhnacademy.frontserver1.common.interceptor.UserInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.nhnacademy.frontserver1.common.interceptor.TokenInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserInfoInterceptor userInfoInterceptor;

    private final TokenInterceptor tokenInterceptor;

    private final CartInfoInterceptor cartInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(userInfoInterceptor)
                .addPathPatterns("/mypage/**")
                .addPathPatterns("/reviews/books/**");

        registry.addInterceptor(cartInfoInterceptor)
            .addPathPatterns("/**");
    }
}
