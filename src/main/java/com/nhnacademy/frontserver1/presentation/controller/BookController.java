package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdapter;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;

    private final BookAdapter bookAdapter;

    @GetMapping("/detail")
    public String detail(Model model) {

        return "product/product-details";
    }

}
