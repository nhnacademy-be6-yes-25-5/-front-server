//package com.nhnacademy.frontserver1.common.interceptor;
//
//import com.nhnacademy.frontserver1.common.decoder.CustomErrorDecoder;
//import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
//import feign.Response;
//import feign.codec.ErrorDecoder;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.Collection;
//
//@Component
//public class JwtResponseInterceptor implements ErrorDecoder {
//
//    private final CookieTokenProvider cookieTokenProvider;
//    private final ErrorDecoder customErrorDecoder;
//
//    public JwtResponseInterceptor(CookieTokenProvider cookieTokenProvider) {
//        this.cookieTokenProvider = cookieTokenProvider;
//        this.customErrorDecoder = new CustomErrorDecoder();
//    }
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
//
//        // 토큰 업데이트 로직
//        updateTokensFromResponse(response, httpServletResponse);
//
//        // 기존 CustomErrorDecoder의 로직 실행
//        return customErrorDecoder.decode(methodKey, response);
//    }
//
//    private void updateTokensFromResponse(Response response, HttpServletResponse httpServletResponse) {
//        String newAccessToken = getFirstHeaderValue(response, HttpHeaders.AUTHORIZATION);
//        String newRefreshToken = getFirstHeaderValue(response, "RefreshToken");
//
//        if (newAccessToken != null) {
//            cookieTokenProvider.setAccessTokenCookie(httpServletResponse, newAccessToken.replace("Bearer ", ""));
//        }
//        if (newRefreshToken != null) {
//            cookieTokenProvider.setRefreshTokenCookie(httpServletResponse, newRefreshToken);
//        }
//    }
//
//    private String getFirstHeaderValue(Response response, String headerName) {
//        Collection<String> headers = response.headers().get(headerName);
//        return headers != null && !headers.isEmpty() ? headers.iterator().next() : null;
//    }
//}