package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CouponService {
    Page<CouponPolicyResponseDTO> findAllCouponPolicies(Pageable pageable);
//    List<CouponUserListResponseDTO> findUserCoupons(Long userId);
    void createCoupon(CouponPolicyRequestDTO createCouponRequest);
//    void deleteCoupon(Long id);
}