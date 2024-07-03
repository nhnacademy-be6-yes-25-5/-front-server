package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CouponAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookDetailCouponResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
//import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/coupons")
public class CouponController {

    private static final Logger log = LoggerFactory.getLogger(CouponController.class);
    private final CouponService couponService;
    private final CouponAdaptor couponAdaptor;
    private final BookService bookService;

    @GetMapping("/policy")
    public String getAdminCouponPolicy(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
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

    @PostMapping("/policy/create")
    public ResponseEntity<Void> createCoupon(@ModelAttribute CouponPolicyRequestDTO createCouponRequest) {
        couponService.createCoupon(createCouponRequest);

        URI redirectUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/coupons/policy")
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);

        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    } //쿠폰 정책 생성

    @GetMapping("/books/{bookId}/coupons")
    public ResponseEntity<List<BookDetailCouponResponseDTO>> getCouponsByBookId(@PathVariable Long bookId) {
        List<Long> categoryIds = bookService.getCategoryIdsByBookId(bookId);
        List<BookDetailCouponResponseDTO> coupons = couponService.getCoupons(bookId, categoryIds);
        return ResponseEntity.ok(coupons);
    }

    @PostMapping("/claim")
    public ResponseEntity<Void> claimCoupon(@RequestParam Long couponId) {
        log.info("claim coupon {}", couponId);
        couponService.claimCoupon(couponId);
        return ResponseEntity.ok().build();
    }

//
//    @GetMapping("/orders/couponPopup")
//    public String getCouponPopup(@RequestParam("userId") Long userId, Model model) {
//        List<CouponUserListResponseDTO> coupons = couponService.findUserCoupons(userId);
//        model.addAttribute("coupons", coupons);
//        return "coupon/popup";
//    } //유저ID에 따른 쿠폰

//
//    @DeleteMapping("/admin/policy/coupon/{id}")
//    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
//        couponService.deleteCoupon(id);
//        return ResponseEntity.noContent().build();
//    }
}
