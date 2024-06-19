package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/orders/couponPopup.html")
    public String getCouponPopup(Model model) {
        List<CouponResponseDTO> coupons = couponService.findAllCoupons();
        model.addAttribute("coupons", coupons);
        return "coupon/popup";
        //todo: coupon policy : coupon 테이블과 매핑해서 하나의 객체로!!
    }
}
