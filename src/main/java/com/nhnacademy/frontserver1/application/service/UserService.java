package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UpdateUserResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;

public interface UserService {

    UserResponse signUp(CreateUserRequest userRequest);

    UserResponse findByUserId();

    UpdateUserResponse updateUser(UpdateUserRequest userRequest);

    void deleteUser(DeleteUserRequest userRequest);
}
