package com.nhnacademy.frontserver1.application.service.impl;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CouponAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.GradeAdaptor;
import com.nhnacademy.frontserver1.application.service.GradeService;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeAdaptor gradeAdaptor;

    @Override
    public GradeResponse getUserGradeByUserId(Long userId) {
        GradeResponse userGradeResponse = gradeAdaptor.getUserGradeByUserId(userId);

        String userName = userGradeResponse.userName();
        Long userGradeId = userGradeResponse.userGradeId();
        String gradeName = userGradeResponse.gradeName();

        return new GradeResponse(userId, userName, userGradeId, gradeName);
    }


}