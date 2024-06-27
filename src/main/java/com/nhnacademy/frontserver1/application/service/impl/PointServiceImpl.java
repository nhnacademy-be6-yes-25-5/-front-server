package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.PointService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PointAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointAdaptor pointAdaptor;

    @Override
    public List<PointLogResponse> getPointLogsByUserId(Long userId) {
        return pointAdaptor.getPointLogsByUserId(userId);
    }
}