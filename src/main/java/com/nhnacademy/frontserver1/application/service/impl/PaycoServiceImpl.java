package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
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
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse.Member;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AuthAdaptor;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaycoServiceImpl {

    private final PaycoAuthAdaptor paycoAuthAdaptor;
    private final PaycoLogoutAdaptor paycoLogoutAdaptor;
    private final PaycoInfoAdaptor paycoInfoAdaptor;
    private final UserAdaptor userAdaptor;
    private final AuthAdaptor authAdaptor;

    public CreateAccessTokenResponse getAccessToken(String grantType, String clientId, String clientSecret, String authorizationCode) {
        return paycoAuthAdaptor.getAccessToken(grantType, clientId, clientSecret, authorizationCode);
    }

    public CreateAccessTokenResponse getRefreshToken(String grantType, String clientId, String clientSecret, String refreshToken) {
        return paycoAuthAdaptor.getAccessToken(grantType, clientId, clientSecret, refreshToken);
    }

    public DeleteAccessTokenResponse deleteAccessToken(String accessToken, String clientId, String clientSecret) {
        return paycoLogoutAdaptor.deleteAccessToken(accessToken, clientId, clientSecret);
    }

    public Member getPaycoInfo(String clientId, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("client_id", clientId);
        headers.add("access_token", accessToken);
        ResponseEntity<CreatePaycoInfoResponse> response = paycoInfoAdaptor.getPaycoInfo(headers);

        return Objects.requireNonNull(response.getBody()).data().member();
    }

    public CreateUserRequest fillMissingInfo(Member paycoInfo, CreateUserRequest form) {
        return form.builder().userName(getValueOrDefault(paycoInfo.name(), form.userName()))
                                                  .userBirth(form.userBirth())
                                                  .userEmail(paycoInfo.id())
                                                  .userPhone(getValueOrDefault(paycoInfo.mobile(), form.userPhone()))
                                                  .userPassword(paycoInfo.id())
                                                  .userConfirmPassword(paycoInfo.id())
                                                    .providerName("PAYCO")
                                                  .build();

    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

}