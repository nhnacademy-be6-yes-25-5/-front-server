package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final BookService bookService;

    @Value("${server.port}")
    private String port;

    @GetMapping
    public String index(Model model, HttpServletRequest request, HttpServletResponse response){

        List<CategoryResponse> rootCategories = categoryService.findRootCategories();
        List<BookResponse> bookList = bookService.findAllBooks();
        Collections.shuffle(bookList);
        List<BookResponse> randomBooks = bookList.stream().limit(6).collect(Collectors.toList());

        model.addAttribute("categories", rootCategories);
        model.addAttribute("bookList", randomBooks);

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String accessToken = cookie.getValue();
                model.addAttribute("AccessToken", accessToken);
            }
            if (cookie.getName().equals("RefreshToken")) {
                String refreshToken = cookie.getValue();
                model.addAttribute("RefreshToken", refreshToken);
            }
        }

        return "index";
    }

    @GetMapping("/category/{categoryId}")
    public String bookCategory(Model model, @PathVariable Long categoryId, @PageableDefault(size = 9, page = 0) Pageable pageable,
                               @RequestParam(defaultValue = "popularity") String sortString ){

        Page<BookResponse> bookList = bookService.getBookByCategoryId(categoryId, pageable, sortString);
        List<CategoryResponse> rootCategories = categoryService.findRootCategories();
        int nowPage = bookList.getNumber();
        int startPage = Math.max(nowPage - 4, 0);
        int endPage = Math.min(nowPage + 5, bookList.getTotalPages() - 1);

        if(bookList.getTotalPages() <= 10) {
            startPage = 0;
            endPage = bookList.getTotalPages() - 1;
        } else {
            if (startPage == 0) {
                endPage = 9;
            } else if (endPage == bookList.getTotalPages() -1) {
                startPage = bookList.getTotalPages() - 10;
            }
        }

        model.addAttribute("sortString", sortString);
        model.addAttribute("bookList", bookList);
        model.addAttribute("nowPage", nowPage + 1);
        model.addAttribute("startPage", startPage + 1);
        model.addAttribute("endPage", endPage + 1);
        model.addAttribute("totalPages", bookList.getTotalPages());
        model.addAttribute("categories", rootCategories);

        return "product/product-list";
    }
}
