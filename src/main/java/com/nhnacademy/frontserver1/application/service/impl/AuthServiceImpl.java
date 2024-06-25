package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 사용자 로그인 정보를 전달받아 인증을 수행하고, 인증 토큰을 생성하여 저장합니다.
     *
     * @param loginUserRequest 로그인 요청 정보가 포함된 DTO
     * @return 생성된 인증 토큰
     */
    @Override
    public String loginUser(LoginUserRequest loginUserRequest) {

        ResponseEntity<String> response = authAdaptor.findLoginUserByEmail(loginUserRequest);

        return response.getBody();
    }

    /**
     * 사용자가 로그인 후 쿠키에 저장된 토큰을 헤더에 담아 api로 전달하는 test용 메소드입니다.
     *
     * @param token 쿠키에 담겨 있던 토큰
     * @return 토큰에 저장되어 있는 정보
     */
    @Override
    public String testToken(String token) {

        ResponseEntity<String> response = authAdaptor.tokenTest(token);

        return response.getBody();
    }

}