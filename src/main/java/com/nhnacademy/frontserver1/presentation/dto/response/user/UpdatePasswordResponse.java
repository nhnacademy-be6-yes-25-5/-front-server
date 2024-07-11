package com.nhnacademy.frontserver1.presentation.dto.response.user;

import lombok.Builder;
import org.springframework.context.annotation.Bean;

@Builder
public record UpdatePasswordResponse(String userPassword, String confirmPassword) {
}
