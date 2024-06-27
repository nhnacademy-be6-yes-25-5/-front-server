package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointService {
    List<PointLogResponse> getPointLogsByUserId(Long userId);







}