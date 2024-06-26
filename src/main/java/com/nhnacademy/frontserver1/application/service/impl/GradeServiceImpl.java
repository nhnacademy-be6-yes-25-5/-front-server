package com.nhnacademy.frontserver1.application.service.impl;
import com.nhnacademy.frontserver1.infrastructure.adaptor.GradeAdaptor;
import com.nhnacademy.frontserver1.application.service.GradeService;
import com.nhnacademy.frontserver1.presentation.dto.response.userGrade.GradeResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeAdaptor GradeAdaptor;
   // private static final Logger logger = LoggerFactory.getLogger(GradeServiceImpl.class);

    @Override
    public GradeResponse getUserGradeByUserId(Long userId) {
        try {
            return GradeAdaptor.getUserGradeByUserId(userId);
        } catch (Exception e) {
          //  logger.error("Error occurred while fetching user grade", e);
            throw e;
        }
    }
}