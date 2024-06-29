package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.PageRequest.of;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/categories")
public class CouponPolicyCategoryController {

    private final CouponService couponService;

    @GetMapping
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          Model model) {
        Pageable pageable = of(page, size);
        Page<CouponPolicyCategoryResponseDTO> categoryCouponsPage = couponService.findAllCategoryCouponPolicies(pageable);

        model.addAttribute("categoryCoupons", categoryCouponsPage.getContent());
        model.addAttribute("currentPage", categoryCouponsPage.getNumber());
        model.addAttribute("totalPages", categoryCouponsPage.getTotalPages());
        model.addAttribute("pageSize", categoryCouponsPage.getSize());
        return "admin/policy/admin-policy-coupon-category";
    }

    @PostMapping("/create")
    public String create(@Validated @RequestBody CouponPolicyCategoryRequestDTO couponPolicyCategoryRequestDTO, BindingResult bindingResult) {
        couponService.createCouponPolicyCategory(couponPolicyCategoryRequestDTO);
        return "redirect:/coupons/policy/categories";
    }
}