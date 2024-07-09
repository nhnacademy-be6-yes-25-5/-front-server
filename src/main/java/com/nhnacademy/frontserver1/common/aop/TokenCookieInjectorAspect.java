//package com.nhnacademy.frontserver1.common.aop;
//
//import jakarta.servlet.http.Cookie;
//import com.nhnacademy.frontserver1.common.context.TokenContext;
//import jakarta.servlet.http.HttpServletResponse;
//import com.nhnacademy.frontserver1.common.provider.CookieTokenProvider;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import java.util.List;
//
///**
// * 토큰을 쿠키에 주입하는 Aspect 클래스입니다.
// * 이 클래스는 컨트롤러 메소드 실행 후 토큰을 쿠키에 추가하는 역할을 합니다.
// */
//@Aspect
//@Component
//public class TokenCookieInjectorAspect {
//
//    private final CookieTokenProvider cookieTokenProvider;
//
//    public TokenCookieInjectorAspect(CookieTokenProvider cookieTokenProvider) {
//        this.cookieTokenProvider = cookieTokenProvider;
//    }
//
//    @Around("execution(* com.nhnacademy.frontserver1.presentation.controller.*.*(..))")
//    public Object injectTokenToCookie(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 쿠키에서 토큰 정보 가져오기
//        List<String> tokens = cookieTokenProvider.getTokenFromCookie(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
//        String accessToken = tokens.get(0);
//        String refreshToken = tokens.get(1);
//
//        // TokenContext에 토큰 정보 설정
//        if (TokenContext.getAccessToken() == null) {
//            TokenContext.setAccessToken(accessToken);
//        }
//        if (TokenContext.getRefreshToken() == null) {
//            TokenContext.setRefreshToken(refreshToken);
//        }
//
//        Object result = joinPoint.proceed();
//
//        // 쿠키에 토큰 정보 추가
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        accessToken = TokenContext.getAccessToken();
//        refreshToken = TokenContext.getRefreshToken();
//        if (accessToken != null && !accessToken.isEmpty() && refreshToken != null && !refreshToken.isEmpty()) {
//            accessToken = accessToken.replace("Bearer ", "");
//            addTokenCookie(response, "AccessToken", accessToken);
//            addTokenCookie(response, "RefreshToken", refreshToken);
//        }
//
//        // 현재 스레드의 토큰 정보 초기화
//
//        return result;
//    }
//
//    private void addTokenCookie(HttpServletResponse response, String name, String token) {
//        if (token != null) {
//            Cookie cookie = new Cookie(name, token);
//            cookie.setHttpOnly(true);
//            //배포시에는 아래 주석 풀기
//            //cookie.setSecure(true);
//            cookie.setPath("/");
//            response.addCookie(cookie);
//        }
//    }
//}