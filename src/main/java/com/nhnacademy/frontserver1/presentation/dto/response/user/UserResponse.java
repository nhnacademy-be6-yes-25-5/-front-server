package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserResponse(Long userId, String userName, String userPhone, String userEmail, LocalDate userBirth,
                           LocalDateTime userRegisterDate, LocalDateTime userLastLoginDate,
                           Long providerId, Long userStateId, Long userGradeId, String userPassword) {
}
