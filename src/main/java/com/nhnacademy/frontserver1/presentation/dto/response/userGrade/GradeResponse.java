package com.nhnacademy.frontserver1.presentation.dto.response.userGrade;

import lombok.Getter;
import lombok.NoArgsConstructor;


public record GradeResponse(Long userId, String userName, Long userGradeId, String gradeName) {
}