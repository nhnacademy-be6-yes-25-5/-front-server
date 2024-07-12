package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UsersAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

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

    boolean findUserPasswordByEmailByName(FindPasswordRequest request);

   // boolean setUserPasswordByEmail(String email, UpdatePasswordRequest request);

    void sendEmail(String recipient);

    Page<UserAddressResponse> getAllUserAddresses(Pageable pageable);

    void updateAddressBased(Long userAddressId, UpdateAddressBasedRequest request);

    CreateUserAddressResponse createUserAddresses(CreateUserAddressRequest userRequest);

    UpdateUserAddressResponse updateUserAddress(Long userAddressId, UpdateUserAddressRequest userRequest);

    UserAddressResponse findUserAddressById(Long userAddressId);

    void deleteUserAddress(Long userAddressId);

    void UpdateUserPasswordByEmail(String email, UpdatePasswordRequest request);
}
