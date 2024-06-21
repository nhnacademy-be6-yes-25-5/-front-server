package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * AuthServiceImpl 클래스는 사용자 인증 기능을 제공하는 서비스 구현체입니다.
 * 이 클래스는 사용자 로그인 기능을 처리하고, 생성된 인증 토큰을 관리합니다.
 *
 * @author NHN Academy
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;
    private final TokenService tokenService;

    /**
     * 사용자 로그인 정보를 전달받아 인증을 수행하고, 인증 토큰을 생성하여 저장합니다.
     *
     * @param loginUserRequest 로그인 요청 정보가 포함된 DTO
     * @return 생성된 인증 토큰
     */
    @Override
    public String loginUser(LoginUserRequest loginUserRequest) {
        ResponseEntity<Void> response = authAdaptor.findLoginUserByEmail(loginUserRequest);

        String authorizationHeader = response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            return tokenService.storeToken(token);
        }

        return authorizationHeader;
    }

}