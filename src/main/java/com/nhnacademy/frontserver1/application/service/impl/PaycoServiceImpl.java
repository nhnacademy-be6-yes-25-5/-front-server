package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.DeleteAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaycoAdaptor;

@Service
@RequiredArgsConstructor
public class PaycoServiceImpl {

    private final PaycoAdaptor paycoAdaptor;

    public CreateAccessTokenResponse getAccessToken(String grantType, String clientId, String clientSecret, String authorizationCode) {
        return paycoAdaptor.getAccessToken(grantType, clientId, clientSecret, authorizationCode);
    }

    public CreateAccessTokenResponse getRefreshToken(String grantType, String clientId, String clientSecret, String refreshToken) {
        return paycoAdaptor.getAccessToken(grantType, clientId, clientSecret, refreshToken);
    }

    public DeleteAccessTokenResponse deleteAccessToken(String accessToken, String clientId, String clientSecret) {
        return paycoAdaptor.deleteAccessToken(accessToken, clientId, clientSecret);
    }

    public CreatePaycoInfoResponse getPaycoInfo(String clientId, String accessToken) {
        return paycoAdaptor.getPaycoInfo(clientId, accessToken);
    }

}