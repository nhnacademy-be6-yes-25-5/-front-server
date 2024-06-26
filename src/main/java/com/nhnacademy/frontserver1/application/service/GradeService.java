package com.nhnacademy.frontserver1.application.service;


import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;

public interface GradeService {
    GradeResponse getUserGradeByUserId(Long userId);
}