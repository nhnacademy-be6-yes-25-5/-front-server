package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponService {
    Page<CouponPolicyResponseDTO> findAllCouponPolicies(Pageable pageable);
    void createCoupon(CouponPolicyRequestDTO createCouponRequest);
    void createCouponPolicyBook(CouponPolicyBookRequestDTO createCouponPolicyBookRequest);
    Page<CouponPolicyBookResponseDTO> findAllBookCouponPolicies(Pageable pageable);
}