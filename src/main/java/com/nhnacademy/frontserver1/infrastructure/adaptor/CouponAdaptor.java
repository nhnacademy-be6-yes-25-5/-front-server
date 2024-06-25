package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
//import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "couponAdaptor", url = "http://localhost:8085/admin-policy")
public interface CouponAdaptor {

    @GetMapping
    List<CouponPolicyResponseDTO> findAll();

    @PostMapping("/coupon")
    void create(@RequestBody CouponPolicyRequestDTO createCouponRequest);

//    @GetMapping("/user-coupons/user")
//    List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId);

//
//    @DeleteMapping("/admin-policy/coupon/{id}")
//    void delete(@PathVariable Long id);
}
