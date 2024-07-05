package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.DeleteAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaycoAuthAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaycoLogoutAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaycoInfoAdaptor;

@Service
@RequiredArgsConstructor
public class PaycoServiceImpl {

    private final PaycoAuthAdaptor paycoAuthAdaptor;
    private final PaycoLogoutAdaptor paycoLogoutAdaptor;
    private final PaycoInfoAdaptor paycoInfoAdaptor;

    public CreateAccessTokenResponse getAccessToken(String grantType, String clientId, String clientSecret, String authorizationCode) {
        return paycoAuthAdaptor.getAccessToken(grantType, clientId, clientSecret, authorizationCode);
    }

    public CreateAccessTokenResponse getRefreshToken(String grantType, String clientId, String clientSecret, String refreshToken) {
        return paycoAuthAdaptor.getAccessToken(grantType, clientId, clientSecret, refreshToken);
    }

    public DeleteAccessTokenResponse deleteAccessToken(String accessToken, String clientId, String clientSecret) {
        return paycoLogoutAdaptor.deleteAccessToken(accessToken, clientId, clientSecret);
    }

    public CreatePaycoInfoResponse getPaycoInfo(String clientId, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("client_id", clientId);
        headers.add("access_token", accessToken);
        ResponseEntity<CreatePaycoInfoResponse> response = paycoInfoAdaptor.getPaycoInfo(headers);

        return response.getBody();

    }

}