package com.nhnacademy.frontserver1.common.config;

import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
import com.nhnacademy.frontserver1.common.interceptor.FeignJwtTokenInterceptor;
import com.nhnacademy.frontserver1.common.decoder.CustomErrorDecoder;
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
    public FeignJwtTokenInterceptor jwtAuthorizationRequestInterceptor(
            CookieTokenProvider cookieTokenProvider) {
        return new FeignJwtTokenInterceptor(cookieTokenProvider);
    }

//    @Bean
//    public Encoder feignFormEncoder() {
//        return new SpringFormEncoder();
//    }

}
