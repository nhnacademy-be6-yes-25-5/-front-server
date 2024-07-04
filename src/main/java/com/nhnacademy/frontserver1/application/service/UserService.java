package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.PointPolicyRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    UserResponse signUp(CreateUserRequest userRequest);

    UserResponse findByUser();

    UpdateUserResponse updateUser(UpdateUserRequest userRequest);

    void deleteUser(DeleteUserRequest userRequest);

//    PointPolicyResponse getPointPolicies(PointPolicyRequest pointPolicyRequest);

    UserGradeResponse getUserGrade();

    Page<PointLogResponse> getPointLogs(Pageable pageable);

    UsersResponse getUserById(Long id);

    Page<UserAddressResponse> getUserAddresses(Long userId, Pageable pageable);

    ReadUserInfoResponse getUserPointsAndGrade();

    Boolean isEmailDuplicate(String email);

    Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable);
}
