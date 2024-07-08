package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.dormant.CreateAuthNumberRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl 클래스는 AuthService 인터페이스를 구현한 사용자 인증 서비스입니다.
 * 이 클래스는 사용자 로그인 처리와 토큰 테스트 기능을 제공합니다.
 *
 * @author NHN Academy
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;

    /**
     * 사용자 로그인을 처리합니다.
     * AuthAdaptor를 통해 로그인 요청을 처리하고 인증 토큰을 반환합니다.
     *
     * @param loginUserRequest 로그인 요청 정보가 포함된 DTO
     * @return 생성된 인증 토큰
     */
    @Override
    public AuthResponse loginUser(LoginUserRequest loginUserRequest) {
        ResponseEntity<AuthResponse> response = authAdaptor.findLoginUserByEmail(loginUserRequest);
        return response.getBody();
    }

    /**
     * 토큰 테스트를 수행합니다.
     * 이 메소드는 사용자가 로그인 후 쿠키에 저장된 토큰을 헤더에 담아 API로 전달하는 테스트용 메소드입니다.
     *
     * @return 토큰에 저장된 정보
     */
    @Override
    public String testToken() {
        ResponseEntity<String> response = authAdaptor.tokenTest();

        return response.getBody();
    }

    @Override
    public void createAuthNumber(CreateAuthNumberRequest request) {
        authAdaptor.createAuthNumber(request);
    }
}