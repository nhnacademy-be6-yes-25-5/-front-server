package com.nhnacademy.frontserver1.common.config;

import com.nhnacademy.frontserver1.common.interceptor.CartInfoInterceptor;
import com.nhnacademy.frontserver1.common.interceptor.UserInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.nhnacademy.frontserver1.common.interceptor.TokenInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

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
            .addPathPatterns("/**")
            .excludePathPatterns("/logout")
            .excludePathPatterns("/auth/error");

        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver("Locale");

        localeResolver.setDefaultLocale(Locale.KOREAN);

        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        interceptor.setParamName("lang");

        return interceptor;
    }
}
