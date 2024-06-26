package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userGradeAdaptor", url = "http://example.com") // URL은 실제 서버의 URL로 변경해야 합니다.
public interface GradeAdaptor {

    @GetMapping("/user/grade")
    GradeResponse getUserGradeByUserId(@RequestParam("userId") Long userId);
}