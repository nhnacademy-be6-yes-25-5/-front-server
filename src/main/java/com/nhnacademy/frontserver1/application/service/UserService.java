package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;


public interface UserService {
    UserResponse getUserById(Long id);
    Page<UserAddressResponse> getUserAddresses(Long userId, Pageable pageable);
    ReadUserInfoResponse getUserPointsAndGrade();
}
