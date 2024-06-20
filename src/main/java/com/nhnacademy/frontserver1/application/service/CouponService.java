package com.nhnacademy.frontserver1.application.service;


import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;

import java.util.List;

public interface CouponService {
    List<CouponUserListResponseDTO> findAllCoupons(Long userId);
//    CouponResponseDTO findCouponById(Long id);
}
