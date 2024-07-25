package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookSearchAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BookSearchController {

    private final BookSearchAdaptor bookSearchAdaptor;
    private final CategoryService categoryService;

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyword, @RequestParam(name = "option") String option,
                         @PageableDefault(size = 9, page = 0) Pageable pageable, @RequestParam(defaultValue = "popularity") String sortString) {

        List<CategoryResponse> rootCategories = categoryService.findRootCategories();

        Page<BookIndexResponse> bookIndexList = getBookIndexPage(pageable, option, keyword, sortString);
        log.info("bookIndexList : {}", bookIndexList.stream().toList());

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

        model.addAttribute("sortString", sortString);
        model.addAttribute("bookList", bookIndexList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        model.addAttribute("nowPage", nowPage + 1);
        model.addAttribute("startPage", startPage + 1);
        model.addAttribute("endPage", endPage + 1);
        model.addAttribute("totalPages", bookIndexList.getTotalPages());
        model.addAttribute("categories", rootCategories);

        return "product/product-search-list";
    }

    private Page<BookIndexResponse> getBookIndexPage(Pageable pageable, String option, String keyword, String sortString) {

        Page<BookIndexResponse> bookIndexList;

        if(option.equals("name")) {

            bookIndexList = bookSearchAdaptor.searchByName(keyword, pageable, sortString);
        } else if(option.equals("description")) {

            bookIndexList = bookSearchAdaptor.searchByDescription(keyword, pageable, sortString);
        } else if(option.equals("author")) {

            bookIndexList = bookSearchAdaptor.searchByAuthorName(keyword, pageable, sortString);
        } else if(option.equals("tag")) {

            bookIndexList = bookSearchAdaptor.searchByTagName(keyword, pageable, sortString);
        } else if(option.equals("category")) {

            bookIndexList = bookSearchAdaptor.searchByCategoryName(keyword, pageable, sortString);
        } else {

            bookIndexList = bookSearchAdaptor.searchAll(keyword, pageable, sortString);

        }

        return bookIndexList;
    }

}
