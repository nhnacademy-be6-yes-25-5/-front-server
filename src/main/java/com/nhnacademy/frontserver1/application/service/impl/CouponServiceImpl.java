package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponAdaptor couponAdaptor;
    private static final String COUPON_API_URL = "http://localhost:8081/coupons";
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Override
    public List<CouponResponseDTO> findAllCoupons() {
        try {
            List<CouponResponseDTO> coupons = couponAdaptor.findAllCoupons();
            if (coupons == null) {
                logger.warn("Received null response for coupons");
                return Collections.emptyList();  // 빈 리스트 반환
            }
            logger.info("Coupons received: {}", coupons); // 추가 로그
            return coupons;
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred while fetching coupons: {}", e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching coupons", e);
            throw e;
        }
    }

//    @Override
//    public CouponResponseDTO findCouponById(Long id) {
//        String url = COUPON_API_URL + "/" + id;
//        try {
//            return restTemplate.getForObject(url, CouponResponseDTO.class);
//        } catch (HttpServerErrorException e) {
//            logger.error("Error occurred while fetching coupon by id: {}", e.getResponseBodyAsString(), e);
//            throw e;
//        } catch (Exception e) {
//            logger.error("Unexpected error occurred while fetching coupon by id", e);
//            throw e;
//        }
//    }
}
