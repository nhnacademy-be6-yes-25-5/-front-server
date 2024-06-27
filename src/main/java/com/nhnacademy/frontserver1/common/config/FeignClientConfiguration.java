//package com.nhnacademy.frontserver1.common.config;
//
//import com.nhnacademy.frontserver1.common.provider.JwtTokenProvider;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.annotation.RequestScope;
//
//@Configuration
//@RequiredArgsConstructor
//public class FeignClientConfiguration {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final HttpServletRequest httpServletRequest;
//
//    @Bean
//    @RequestScope
//    public RequestInterceptor requestInterceptor() {
//        return this::addTokenToHeader;
//    }
//
//    private void addTokenToHeader(RequestTemplate template) {
//        String token = jwtTokenProvider.getTokenFromRequest(httpServletRequest);
//        if (token != null) {
//            template.header("Authorization", "Bearer " + token);
//        }
//    }
//}