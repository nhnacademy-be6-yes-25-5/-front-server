package com.nhnacademy.frontserver1.presentation.dto.response.user;

import com.nhnacademy.frontserver1.presentation.dto.response.address.UsersAddressResponse;

import java.util.List;

public record UsersResponse(Long userId, String userName, String userGrade, int userPoints, UsersAddressResponse defaultAddress, List<UsersAddressResponse> addresses) {
}
