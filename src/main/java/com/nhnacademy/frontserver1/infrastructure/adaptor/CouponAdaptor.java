package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookDetailCouponResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyCategoryResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyResponseDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "couponAdaptor", url = "${eureka.gateway}/coupons")
public interface CouponAdaptor {

    @GetMapping("/policy")
    List<CouponPolicyResponseDTO> findAll();

    @PostMapping("/policy/create")
    void create(@RequestBody CouponPolicyRequestDTO createCouponRequest);

    @GetMapping("/policy/books")
    List<CouponPolicyBookResponseDTO> findAllBooks();

    @PostMapping("/policy/books/create")
    CouponPolicyBookResponseDTO create(@RequestBody CouponPolicyBookRequestDTO requestDTO);

    @GetMapping("/policy/categories")
    List<CouponPolicyCategoryResponseDTO> findAllCategories();

    @PostMapping("/policy/categories/create")
    CouponPolicyCategoryResponseDTO create(@RequestBody CouponPolicyCategoryRequestDTO requestDTO);

    @GetMapping
    List<BookDetailCouponResponseDTO> getCouponsByBookIdAndCategoryIds(@RequestParam("bookId") Long bookId, @RequestParam("categoryIds") List<Long> categoryIds);

//    @GetMapping("/user-coupons/user")
//    List<CouponUserListResponseDTO> findUserCoupons(@RequestParam("userId") Long userId);

//
//    @DeleteMapping("/admin-policy/coupon/{id}")
//    void delete(@PathVariable Long id);
}
