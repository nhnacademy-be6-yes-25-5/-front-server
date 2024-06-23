//package com.nhnacademy.frontserver1.common.config.util;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * TokenParser 클래스는 JWT(JSON Web Token)를 파싱하여 토큰에 포함된 사용자 정보를 추출하는 유틸리티 클래스입니다.
// * 이 클래스는 사용자 ID, 사용자 역할, 로그인 상태와 같은 정보를 토큰에서 추출할 수 있습니다.
// *
// * @author lettuce82
// * @version 1.0
// */
//@Component
//public class TokenParser {
//
//    @Value("${spring.jwt.secret}")
//    private String secret;
//
//    /**
//     * 토큰에서 사용자 ID를 추출합니다.
//     *
//     * @param token 사용자 토큰
//     * @return 사용자 ID
//     */
//    public Long getUserId(String token) {
//        Claims claims = getClaims(token);
//        return claims.get("userId", Long.class);
//    }
//
//    /**
//     * 토큰에서 사용자 역할을 추출합니다.
//     *
//     * @param token 사용자 토큰
//     * @return 사용자 역할
//     */
//    public String getUserRole(String token) {
//        Claims claims = getClaims(token);
//        return claims.get("userRole", String.class);
//    }
//
//    /**
//     * 토큰에서 사용자 로그인 상태를 추출합니다.
//     *
//     * @param token 사용자 토큰
//     * @return 사용자 로그인 상태
//     */
//    public String getLoginStatus(String token) {
//        Claims claims = getClaims(token);
//        return claims.get("loginStatus", String.class);
//    }
//
//    /**
//     * JWT 토큰에서 Claims 정보를 추출합니다.
//     *
//     * @param token 사용자 토큰
//     * @return JWT 토큰의 Claims 정보
//     */
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}