//package com.nhnacademy.frontserver1.infrastructure.filter;
//
//import com.nhnacademy.frontserver1.application.service.impl.TokenService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * AuthenticationFilter 클래스는 사용자 인증 과정을 처리하는 Spring Security 필터입니다.
// * 이 필터는 요청 URI에 따라 인증 검사 여부를 결정하며, 인증이 필요한 경우 토큰을 검증합니다.
// *
// * @author lettuce82
// * @version 1.0
// */
//@Component
//public class AuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private TokenService tokenService;
//
//    private static final String[] RESOURCE_PATTERNS = {
//            "/images/.*",
//            "/css/.*",
//            "/js/.*"
//    };
//
//    /**
//     * 요청 URI에 따라 인증 검사를 처리합니다.
//     *
//     * 1. /auth/login 엔드포인트로의 요청은 인증 헤더가 있으면 허용하지 않습니다.
//     * 2. /index 엔드포인트로의 요청은 인증 검사를 건너뜁니다.
//     * 3. 리소스 요청 URI(이미지, CSS, JavaScript 파일 등)인 경우에도 인증 검사를 건너뜁니다.
//     * 4. 그 외의 요청에 대해서는 사용자 인증 토큰을 검증합니다.
//     *    - 유효한 토큰이 있는 경우 요청을 계속 진행합니다.
//     *    - 유효한 토큰이 없는 경우 401 Unauthorized 응답을 반환합니다.
//     *
//     * @param request  HTTP 요청 객체
//     * @param response HTTP 응답 객체
//     * @param filterChain 필터 체인
//     * @throws ServletException Servlet 예외 발생 시
//     * @throws IOException I/O 예외 발생 시
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        if (request.getRequestURI().equals("/auth/login")) {
//            String authHeader = request.getHeader("Authentication");
//            if (authHeader != null) {
//                // 인증 헤더가 있으면 /auth/login 엔드포인트 접근 불가
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            } else {
//                filterChain.doFilter(request, response);
//            }
//            return;
//        }
//
//        if (request.getRequestURI().equals("/index")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        if (isResourceRequest(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        String authHeader = request.getHeader("Authentication");
//        if (authHeader != null) {
//            String token = tokenService.getToken(authHeader);
//            if (token != null && !token.isEmpty()) {
//                filterChain.doFilter(request, response);
//            } else {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            }
//        } else {
//            // 인증 헤더가 없는 경우, 로그인이 필요하다는 메시지 출력 후 로그인 페이지로 리다이렉트
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getWriter().write("로그인이 필요한 기능입니다!");
//            response.sendRedirect("/auth/login");
//        }
//    }
//
//    /**
//     * 요청 URI가 리소스 요청 URI 패턴에 일치하는지 확인합니다.
//     *
//     * @param uri 확인할 요청 URI
//     * @return 리소스 요청 URI 패턴에 일치하는 경우 true, 그렇지 않은 경우 false
//     */
//    private boolean isResourceRequest(String uri) {
//        for (String pattern : RESOURCE_PATTERNS) {
//            if (uri.matches(pattern)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//}