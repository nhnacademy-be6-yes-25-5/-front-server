package com.nhnacademy.frontserver1.application.service;


import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponResponseDTO;

import java.util.List;

public interface CouponService {
    List<CouponResponseDTO> findAllCoupons();
//    CouponResponseDTO findCouponById(Long id);
}
