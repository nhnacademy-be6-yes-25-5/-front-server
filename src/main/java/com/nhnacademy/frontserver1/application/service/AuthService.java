package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    String loginUser(@RequestBody LoginUserRequest loginUserRequest);

    String testToken(@PathVariable String testMent);
}
