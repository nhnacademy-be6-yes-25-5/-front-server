package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/orders/couponPopup.html")
    public String getCouponPopup(@RequestParam("userId") Long userId, Model model) {
        List<CouponUserListResponseDTO> coupons = couponService.findUserCoupons(userId);
        model.addAttribute("coupons", coupons);
        return "coupon/popup";
    }
}
