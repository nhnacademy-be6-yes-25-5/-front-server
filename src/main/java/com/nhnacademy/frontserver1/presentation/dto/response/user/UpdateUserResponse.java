package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UpdateUserResponse(String userName, LocalDate userBirth, String userPhone) {
}
