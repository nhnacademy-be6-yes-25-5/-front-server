package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UsersAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    UserResponse signUp(CreateUserRequest userRequest);

    UserResponse findByUser();

    UpdateUserResponse updateUser(UpdateUserRequest userRequest);

    void deleteUser(DeleteUserRequest userRequest);

//    PointPolicyResponse getPointPolicies(PointPolicyRequest pointPolicyRequest);

    UserGradeResponse getUserGrade();

    Page<PointLogResponse> getPointLogs(Pageable pageable);

    UsersResponse getUserById(Long id);

    Page<UsersAddressResponse> getUserAddresses(Long userId, Pageable pageable);

    ReadUserInfoResponse getUserPointsAndGrade();

    List<FindUserResponse> findAllUserEmailByUserNameByUserPhone(String name, String phone, Pageable pageable);

    Boolean isEmailDuplicate(String email);

    Page<CouponBoxResponse> getStateCouponBox(String couponState, Pageable pageable);

    Page<UserAddressResponse> getAllUserAddresses(Pageable pageable);

    void updateAddressBased(Long userAddressId, UpdateAddressBasedRequest request);

    CreateUserAddressResponse createUserAddresses(CreateUserAddressRequest userRequest);
}
