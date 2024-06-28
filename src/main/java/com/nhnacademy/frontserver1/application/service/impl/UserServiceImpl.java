package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.UserAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UpdateUserResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserGradeResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserAdaptor userAdaptor;

    @Override
    public UserResponse signUp(CreateUserRequest userRequest) {

        return userAdaptor.signUp(userRequest);
    }

    @Override
    public UserResponse findByUserId() {
        return userAdaptor.findByUserId();
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest userRequest) {
        return userAdaptor.updateUser(userRequest);
    }

    @Override
    public void deleteUser(DeleteUserRequest userRequest) {
        userAdaptor.deleteUser(userRequest);
    }

    @Override
    public PointPolicyResponse getPointPolicies(PointPolicyRequest pointPolicyRequest) {
        return null;
    }

    @Override
    public UserGradeResponse getUserGrade() {
        return userAdaptor.getUserGrade();
    }
}
