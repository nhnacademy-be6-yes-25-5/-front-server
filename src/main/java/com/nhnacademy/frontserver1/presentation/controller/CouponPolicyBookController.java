package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.PageRequest.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/books")
public class CouponPolicyBookController {

    private final CouponService couponService;

    @GetMapping
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          Model model) {
        Pageable pageable = of(page, size);
        Page<CouponPolicyBookResponseDTO> bookCouponsPage = couponService.findAllBookCouponPolicies(pageable);

        model.addAttribute("bookCoupons", bookCouponsPage.getContent());
        model.addAttribute("currentPage", bookCouponsPage.getNumber());
        model.addAttribute("totalPages", bookCouponsPage.getTotalPages());
        model.addAttribute("pageSize", bookCouponsPage.getSize());
        return "admin/policy/admin-policy-coupon-book";
    }

    @PostMapping("/create")
    public String create(@Validated @RequestBody CouponPolicyBookRequestDTO couponPolicyBookRequestDTO) {
        couponService.createCouponPolicyBook(couponPolicyBookRequestDTO);
        return "redirect:/coupons/policy/books";
    }
}