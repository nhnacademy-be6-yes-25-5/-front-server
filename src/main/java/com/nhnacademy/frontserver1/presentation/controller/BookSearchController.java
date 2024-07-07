package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookSearchAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.book.BookSearchRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchAdaptor bookSearchAdaptor;
    private final CategoryService categoryService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyword, @RequestParam(name = "option") String option, @PageableDefault(size = 9, page = 0) Pageable pageable) {

        Page<BookIndexResponse> bookIndexList;
        List<CategoryResponse> rootCategories = categoryService.findRootCategories();

        if(option.equals("name")) {

            bookIndexList = bookSearchAdaptor.searchByName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("description")) {

            bookIndexList = bookSearchAdaptor.searchByDescription(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);
        } else if(option.equals("author")) {

            bookIndexList = bookSearchAdaptor.searchByAuthorName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("tag")) {

            bookIndexList = bookSearchAdaptor.searchByTagName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("category")) {

            bookIndexList = bookSearchAdaptor.searchByCategoryName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);
        } else {

            bookIndexList = bookSearchAdaptor.searchAll(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        }

        int nowPage = bookIndexList.getNumber();
        int startPage = Math.max(nowPage - 4, 0);
        int endPage = Math.min(nowPage + 5, bookIndexList.getTotalPages() - 1);

        if(bookIndexList.getTotalPages() <= 10) {
            startPage = 0;
            endPage = bookIndexList.getTotalPages() - 1;
        } else {
            if (startPage == 0) {
                endPage = 9;
            } else if (endPage == bookIndexList.getTotalPages() -1) {
                startPage = bookIndexList.getTotalPages() - 10;
            }
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        model.addAttribute("nowPage", nowPage + 1);
        model.addAttribute("startPage", startPage + 1);
        model.addAttribute("endPage", endPage + 1);
        model.addAttribute("totalPages", bookIndexList.getTotalPages());
        model.addAttribute("categories", rootCategories);

        return "product/product-search-list";
    }
}
