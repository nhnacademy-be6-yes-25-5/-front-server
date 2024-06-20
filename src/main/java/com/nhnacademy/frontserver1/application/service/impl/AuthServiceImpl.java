package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthAdaptor authAdaptor;
    private final TokenService tokenService;

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