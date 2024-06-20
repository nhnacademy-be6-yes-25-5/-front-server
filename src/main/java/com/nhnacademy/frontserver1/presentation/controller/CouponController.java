package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/orders/couponPopup")
    public String getCouponPopup(@RequestParam("userId") Long userId, Model model) {
        List<CouponUserListResponseDTO> coupons = couponService.findUserCoupons(userId);
        model.addAttribute("coupons", coupons);
        return "coupon/popup";
    } //유저ID에 따른 쿠폰

    @GetMapping("/admin/policy/admin-coupon-policy")
    public String getAdminCouponPolicy(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CouponPolicyResponseDTO> couponPage = couponService.findAllCouponPolicies(pageable);

        model.addAttribute("coupons", couponPage.getContent());
        model.addAttribute("currentPage", couponPage.getNumber());
        model.addAttribute("totalPages", couponPage.getTotalPages());
        model.addAttribute("pageSize", couponPage.getSize());
        model.addAttribute("newCouponPolicy", new CouponPolicyRequestDTO());
        return "admin/policy/admin-policy-coupon";
    } //생성한 쿠폰 정책 목록

    @PostMapping("/admin-policy/coupon")
    public ResponseEntity<Void> createCoupon(@ModelAttribute CouponPolicyRequestDTO createCouponRequest) {
        couponService.createCoupon(createCouponRequest);

        URI redirectUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/admin/policy/admin-coupon-policy")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    } //쿠폰 정책 생성

    @DeleteMapping("/admin/policy/coupon/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}
