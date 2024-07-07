package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.infrastructure.adaptor.BookSearchAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.book.BookSearchRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchAdaptor bookSearchAdaptor;

    @GetMapping("/search")
    public String search(Model model, @RequestParam String keyword, @RequestParam(name = "option") String option, Pageable pageable) {

        if(option.equals("name")) {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("description")) {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByDescription(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);
        } else if(option.equals("author")) {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByAuthorName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("tag")) {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByTagName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        } else if(option.equals("category")) {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByCategoryName(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);
        } else {

            Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchAll(keyword, pageable);

            model.addAttribute("bookList", bookIndexList);

        }

        return "product/product-list";
    }
}
