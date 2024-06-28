package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UpdateUserResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserGradeResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface UserAdaptor {

    @PostMapping("/users/sign-up")
    UserResponse signUp(@RequestBody CreateUserRequest userRequest);

    @GetMapping("/users")
    UserResponse findByUserId();

    @PutMapping("/users")
    UpdateUserResponse updateUser(@RequestBody UpdateUserRequest userRequest);

    @DeleteMapping("/users")
    void deleteUser(@RequestBody DeleteUserRequest userRequest);

    @GetMapping("/orders/info")
    ReadOrderUserInfoResponse getUserInfo();

    @GetMapping("/grades")
    UserGradeResponse getUserGrade();
}
