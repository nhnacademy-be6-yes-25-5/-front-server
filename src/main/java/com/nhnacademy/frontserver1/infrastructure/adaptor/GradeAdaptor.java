package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userGradeAdaptor", url = "http://localhost:8085")
public interface GradeAdaptor {

    @GetMapping("/user/grade/{userId}")
    GradeResponse getUserGradeByUserId(@RequestParam("userId") Long userId);
}