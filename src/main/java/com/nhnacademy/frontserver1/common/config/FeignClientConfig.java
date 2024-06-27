package com.nhnacademy.frontserver1.common.config;

import com.nhnacademy.frontserver1.common.decoder.CustomErrorDecoder;
import com.nhnacademy.frontserver1.common.interceptor.JwtAuthorizationRequestInterceptor;
import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public JwtAuthorizationRequestInterceptor jwtAuthorizationRequestInterceptor(
        CookieTokenProvider cookieTokenProvider) {
        return new JwtAuthorizationRequestInterceptor(cookieTokenProvider);
    }

    @Bean
    public RequestInterceptor requestInterceptor(
        JwtAuthorizationRequestInterceptor interceptor) {
        return interceptor;
    }
}
