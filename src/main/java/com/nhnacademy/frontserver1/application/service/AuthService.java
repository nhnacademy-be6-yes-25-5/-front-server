package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.dormant.CreateAuthNumberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.dormant.SubmitAuthNumberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    AuthResponse loginUser(@RequestBody LoginUserRequest loginUserRequest);

    String testToken();

    void createAuthNumber(CreateAuthNumberRequest request);

    boolean submitAuthNumber(SubmitAuthNumberRequest request);
}
