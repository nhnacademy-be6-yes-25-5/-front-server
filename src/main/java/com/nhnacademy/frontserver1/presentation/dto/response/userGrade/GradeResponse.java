package com.nhnacademy.frontserver1.presentation.dto.response.userGrade;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeResponse {
    private Long userGradeId;
    private String gradeName;
    private String gradeDescription;
}