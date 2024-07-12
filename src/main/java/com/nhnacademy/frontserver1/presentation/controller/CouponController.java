package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookDetailCouponResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final BookService bookService;

    // 모든 쿠폰 정책
    @GetMapping("/policy")
    public String getAdminCouponPolicy(@RequestParam(defaultValue = "ko") String lang,
                                       @PageableDefault(size = 10, page = 0) Pageable pageable,
                                       Model model) {
        Page<CouponPolicyResponseDTO> couponPage = couponService.findAllCouponPolicies(pageable);

        model.addAttribute("coupons", couponPage.getContent());
        model.addAttribute("currentPage", couponPage.getNumber());
        model.addAttribute("totalPages", couponPage.getTotalPages());
        model.addAttribute("pageSize", couponPage.getSize());
        model.addAttribute("newCouponPolicy", new CouponPolicyRequestDTO());
        model.addAttribute("lang", lang); // 언어 파라미터 추가
        return "admin/policy/admin-policy-coupon";
    }

    // 일반 쿠폰 정책 생성
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
    }

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
}
