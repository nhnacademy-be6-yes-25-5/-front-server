package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;

    @Override
    public ResponseEntity<Void> findLoginUserByEmail(LoginUserRequest loginUserRequest) {
        return authAdaptor.findLoginUserByEmail(loginUserRequest);
    }

}
