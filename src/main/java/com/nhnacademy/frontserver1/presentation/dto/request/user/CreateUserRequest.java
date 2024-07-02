package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CreateUserRequest(String userName, LocalDate userBirth, String userEmail,
                                String userPhone, String userPassword, String userConfirmPassword) {
}

