package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdaptor userAdaptor;


    @Override
    public ReadUserInfoResponse getUserPointsAndGrade() {
//        return userAdaptor.getUserPointsAndGrade();

        return ReadUserInfoResponse.fromTest();
    }
}
