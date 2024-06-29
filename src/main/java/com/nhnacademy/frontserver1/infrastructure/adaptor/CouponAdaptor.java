package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.application.service.dto.request.ReadMaximumDiscountCouponRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyCategoryResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
//import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponUserListResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMaximumDiscountCouponResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "couponAdaptor", url = "${eureka.gateway}/coupons")
public interface CouponAdaptor {

    @GetMapping("/policy")
    List<CouponPolicyResponseDTO> findAll();

    @PostMapping("/policy/create")
    void create(@RequestBody CouponPolicyRequestDTO createCouponRequest);

    @GetMapping("/max")
    ReadMaximumDiscountCouponResponse getMaxDiscountCouponByTotalAmount(
        @RequestBody ReadMaximumDiscountCouponRequest request);

    @GetMapping("/policy/books")
    List<CouponPolicyBookResponseDTO> findAllBooks();

    @PostMapping("/policy/books/create")
    CouponPolicyBookResponseDTO create(@RequestBody CouponPolicyBookRequestDTO requestDTO);

    @GetMapping("/policy/categories")
    List<CouponPolicyCategoryResponseDTO> findAllCategories();

    @PostMapping("/policy/categories/create")
    CouponPolicyCategoryResponseDTO create(@RequestBody CouponPolicyCategoryRequestDTO requestDTO);

//    @GetMapping("/user-coupons/user")
//    List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId);

//
//    @DeleteMapping("/admin-policy/coupon/{id}")
//    void delete(@PathVariable Long id);
}
