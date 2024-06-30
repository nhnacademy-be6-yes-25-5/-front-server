package com.nhnacademy.frontserver1.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.nhnacademy.frontserver1.presentation.dto.request.auth.CreateAccessTokenRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;

@Component
@RequiredArgsConstructor
public class TokenRefresher {
    private final AuthAdaptor authAdaptor;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public void refreshToken() {
        String refreshToken = request.getHeader("RefreshToken");

        CreateAccessTokenRequest tokenRequest = new CreateAccessTokenRequest(refreshToken);
        CreateAccessTokenResponse newAccessToken = authAdaptor.refreshToken(tokenRequest);

        response.setHeader("Authorization", "Bearer " + newAccessToken.accessToken());
    }
}