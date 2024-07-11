package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CouponService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.coupon.CouponPolicyBookRequestDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookCouponResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.CouponPolicyBookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons/policy/books")
public class CouponPolicyBookController {

    private final CouponService couponService;
    private final BookAdaptor bookAdapter;

    @GetMapping
    public String findAll(@RequestParam(defaultValue = "ko") String lang,
                          Pageable pageable,
                          Model model) {
        Page<CouponPolicyBookResponseDTO> bookCouponsPage = couponService.findAllBookCouponPolicies(pageable);

        model.addAttribute("bookCoupons", bookCouponsPage.getContent());
        model.addAttribute("currentPage", bookCouponsPage.getNumber());
        model.addAttribute("totalPages", bookCouponsPage.getTotalPages());
        model.addAttribute("pageSize", bookCouponsPage.getSize());
        model.addAttribute("lang", lang);
        return "admin/policy/admin-policy-coupon-book";
    }

    @PostMapping("/create")
    public String create(@Validated @RequestBody CouponPolicyBookRequestDTO couponPolicyBookRequestDTO) {
        couponService.createCouponPolicyBook(couponPolicyBookRequestDTO);
        return "redirect:/coupons/policy/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query", required = false, defaultValue = "") String query,
                              @RequestParam(defaultValue = "ko") String lang,
                              Model model) {
        List<BookCouponResponseDTO> books = bookAdapter.findBooksByName(query);
        model.addAttribute("bookList", books);
        model.addAttribute("keyword", query);
        model.addAttribute("lang", lang);
        return "admin/policy/admin-policy-coupon-book-search";
    }
}
