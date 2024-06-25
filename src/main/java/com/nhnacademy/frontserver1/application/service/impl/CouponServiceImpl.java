package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponAdaptor couponAdaptor;
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Override
    public Page<CouponPolicyResponseDTO> findAllCouponPolicies(Pageable pageable) {
        try {
            List<CouponPolicyResponseDTO> coupons = couponAdaptor.findAll();
            if (coupons == null) {
                logger.warn("Received null response for coupons");
                coupons = Collections.emptyList();
            }
            logger.info("Coupons received: {}", coupons);

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), coupons.size());
            List<CouponPolicyResponseDTO> pagedCoupons = coupons.subList(start, end);

            return new PageImpl<>(pagedCoupons, pageable, coupons.size());
        } catch (HttpServerErrorException e) {
            logger.error("Error occurred while fetching coupons: {}", e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching coupons", e);
            throw e;
        }
    }

    @Override
    public void createCoupon(CouponPolicyRequestDTO createCouponRequest) {
        couponAdaptor.create(createCouponRequest);
    }

//
//    @Override
//    public List<CouponUserListResponseDTO> findUserCoupons(Long userId) {
//        try {
//            return couponAdaptor.findUserCoupons(userId);
//        } catch (FeignClientException e) {
//            logger.error("Error occurred while fetching user coupons", e);
//            throw e;
//        } catch (Exception e) {
//            logger.error("Unexpected error occurred while fetching user coupons", e);
//            throw e;
//        }
//    }


//    @Override
//    public void deleteCoupon(Long id) {
//        couponAdaptor.delete(id);
//    }

}
