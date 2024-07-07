package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.DeleteAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
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

    public boolean isRequiredInfoMissing(Member paycoInfo) {
        return paycoInfo.email() == null || paycoInfo.mobile() == null ||
                paycoInfo.name() == null || paycoInfo.birthdayMMdd() == null;
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
                                                  .userBirth((LocalDate) getValueOrDefault(paycoInfo.birthdayMMdd(), form.userBirth()))
                                                  .userEmail(paycoInfo.id())
                                                  .userPhone(getValueOrDefault(paycoInfo.mobile(), form.userPhone()))
                                                  .userPassword(paycoInfo.id())
                                                  .userConfirmPassword(paycoInfo.id())
                                                  .build();

    }

    private <T> T getValueOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public AuthResponse processPaycoLogin(Member paycoInfo) {
        CreateUserRequest request = CreateUserRequest.builder()
                .userName(paycoInfo.name())
                .userBirth(parseBirthday(paycoInfo.birthdayMMdd()))
                .userEmail(paycoInfo.id())
                .userPhone(formatPhoneNumber(paycoInfo.mobile()))
                .userPassword(paycoInfo.id())
                .userConfirmPassword(paycoInfo.id())
                .providerName("PAYCO")
                .build();

        if (userAdaptor.checkEmail(paycoInfo.id())) {
            // 회원 정보가 있으면 로그인 요청
            LoginUserRequest loginUserRequest = LoginUserRequest.builder()
                    .email(request.userEmail())
                    .password(request.userPassword())
                    .build();
            return authAdaptor.findLoginUserByEmail(loginUserRequest).getBody();
        } else {
            // 회원 정보가 없으면 회원가입 후 로그인 요청
            userAdaptor.signUp(request);
            LoginUserRequest loginUserRequest = LoginUserRequest.builder()
                    .email(request.userEmail())
                    .password(request.userPassword())
                    .build();
            return authAdaptor.findLoginUserByEmail(loginUserRequest).getBody();
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("\\D", "");

        if (phoneNumber.length() == 11) {
            return "010-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);
        } else if (phoneNumber.length() > 11) {
            phoneNumber = phoneNumber.substring(phoneNumber.length() - 11);
            return "010-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7);
        } else {
            return phoneNumber;
        }
    }

    private LocalDate parseBirthday(String mmdd) {
        if (mmdd == null || mmdd.length() != 4) {
            throw new IllegalArgumentException("Invalid birthday format. Expected MMDD, got: " + mmdd);
        }

        int currentYear = Year.now().getValue();
        String fullDate = currentYear + mmdd;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate parsedDate = LocalDate.parse(fullDate, formatter);

        if (parsedDate.isAfter(LocalDate.now())) {
            parsedDate = parsedDate.minusYears(1);
        }

        return parsedDate;
    }

}