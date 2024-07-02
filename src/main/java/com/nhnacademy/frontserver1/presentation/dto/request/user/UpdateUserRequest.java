package com.nhnacademy.frontserver1.presentation.dto.request.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateUserRequest(String userName, String userPhone, LocalDate userBirth, String userPassword,
                                String newUserPassword, String newUserConfirmPassword) {
}
