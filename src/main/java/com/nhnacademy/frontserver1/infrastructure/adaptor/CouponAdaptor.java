package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "couponAdaptor", url = "http://localhost:8085")
public interface CouponAdaptor {

    @GetMapping("/coupons")
    List<CouponUserListResponseDTO> findAllCoupons();

    @GetMapping("/user-coupons/user")
    List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId);
}
