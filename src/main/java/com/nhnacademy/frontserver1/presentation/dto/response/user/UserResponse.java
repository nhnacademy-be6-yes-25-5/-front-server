package com.nhnacademy.frontserver1.presentation.dto.response.user;

import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;

import java.util.List;


public record UserResponse(Long userId, String userName, String userGrade, int userPoints, UserAddressResponse defaultAddress, List<UserAddressResponse> addresses) {}