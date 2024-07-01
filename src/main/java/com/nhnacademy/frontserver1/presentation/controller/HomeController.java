package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String index(Model model){

        List<CategoryResponse> rootCategories = categoryService.findRootCategories();
        List<BookResponse> bookList = bookService.findAllBooks();
        Collections.shuffle(bookList);
        List<BookResponse> randomBooks = bookList.stream().limit(6).collect(Collectors.toList());

        model.addAttribute("categories", rootCategories);
        model.addAttribute("bookList", randomBooks);

        return "index";
    }

    @GetMapping("/product/list/{categoryId}")
    public String bookCategory(Model model, @PathVariable Long categoryId, @PageableDefault(size = 8, page = 0) Pageable pageable){

        Page<BookResponse> bookList = bookService.getBookByCategoryId(categoryId, pageable);
        int nowPage = bookList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, bookList.getTotalPages());

        model.addAttribute("bookList", bookList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "product/product-list";
    }

    @GetMapping("/product/grid/{categoryId}")
    public String bookCategoryGrid(Model model, @PathVariable Long categoryId){
        return "product-grids";
    }
}
