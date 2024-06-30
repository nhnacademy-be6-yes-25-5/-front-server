package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.auth.CreateAccessTokenRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authAdaptor", url = "${eureka.gateway}/auth")
public interface AuthAdaptor {

    @PostMapping("/login")
    ResponseEntity<AuthResponse> findLoginUserByEmail(@RequestBody LoginUserRequest loginUserRequest);

    @GetMapping("/test")
    ResponseEntity<String> tokenTest();

    @PostMapping("/refresh")
    CreateAccessTokenResponse refreshToken(@RequestBody CreateAccessTokenRequest request);

}