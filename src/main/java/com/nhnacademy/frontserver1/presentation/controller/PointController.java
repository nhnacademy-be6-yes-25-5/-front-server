package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.PointService;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/points")
    public String findAllPoints(Model model) {
        Long userId = 28L; // 예제용으로 사용자 ID를 설정합니다.
        List<PointLogResponse> pointLogs = pointService.getPointLogsByUserId(userId);

//        // 예제용으로 현재 포인트 값을 설정합니다. 실제로는 서비스에서 값을 가져와야 합니다.
//        BigDecimal currentPoint = pointLogs.stream()
//                .map(PointLogResponse::getPointLogAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        model.addAttribute("pointLogs", pointLogs);
//        model.addAttribute("currentPoint", currentPoint);

        return "mypage-orderPoints";
    }
}
