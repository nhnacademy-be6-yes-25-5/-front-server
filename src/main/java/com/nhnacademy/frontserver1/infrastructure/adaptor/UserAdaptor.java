package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UpdateUserResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userAdaptor", url = "http://localhost:8085/users")
public interface UserAdaptor {

    @PostMapping("/users/sign-up")
    UserResponse signUp(@RequestBody CreateUserRequest userRequest);

    @GetMapping("/users")
    UserResponse findByUserId();

    @PutMapping("/users")
    UpdateUserResponse updateUser(@RequestBody UpdateUserRequest userRequest);

    @DeleteMapping("/users")
    void deleteUser(@RequestBody DeleteUserRequest userRequest);

    @GetMapping("/admin/point-policies/{pointPolicyId}")
    PointPolicyResponse getPointPolicy(@PathVariable Long pointPolicyId);
}
