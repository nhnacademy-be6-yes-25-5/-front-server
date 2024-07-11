package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyCategoryRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/categories")
public class CouponPolicyCategoryController {

    private final CouponService couponService;
    private final BookAdaptor bookAdapter;

    @GetMapping
    public String findAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(defaultValue = "ko") String lang,
                          Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CouponPolicyCategoryResponseDTO> categoryCouponsPage = couponService.findAllCategoryCouponPolicies(pageable);

        model.addAttribute("categoryCoupons", categoryCouponsPage.getContent());
        model.addAttribute("currentPage", categoryCouponsPage.getNumber());
        model.addAttribute("totalPages", categoryCouponsPage.getTotalPages());
        model.addAttribute("pageSize", categoryCouponsPage.getSize());
        model.addAttribute("lang", lang);
        return "admin/policy/admin-policy-coupon-category";
    }

    @PostMapping("/create")
    public String create(@Validated @RequestBody CouponPolicyCategoryRequestDTO couponPolicyCategoryRequestDTO, BindingResult bindingResult) {
        couponService.createCouponPolicyCategory(couponPolicyCategoryRequestDTO);
        return "redirect:/coupons/policy/categories";
    }

    @GetMapping("/search")
    public String searchCategories(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                                   @RequestParam(defaultValue = "ko") String lang,
                                   Model model) {
        List<CategoryResponse> categories = bookAdapter.findAllCategories();
        model.addAttribute("categoryList", categories);
        model.addAttribute("keyword", query);
        model.addAttribute("lang", lang);
        return "admin/policy/admin-policy-coupon-category-search";
    }
}