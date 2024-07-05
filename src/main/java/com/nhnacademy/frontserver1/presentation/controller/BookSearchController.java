package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.infrastructure.adaptor.BookSearchAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.book.BookSearchRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookIndexResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchAdaptor bookSearchAdaptor;

    @PostMapping("/searchAll")
    public String searchAll(Model model, @ModelAttribute BookSearchRequest request) {

        Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchAll(request);

        model.addAttribute("bookIndexList", bookIndexList);

        return "";

    }

    @PostMapping("/searchByName")
    public String searchByName(Model model, @ModelAttribute BookSearchRequest request) {

        Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByName(request);

        model.addAttribute("bookIndexList", bookIndexList);

        return "";

    }

    @PostMapping("/searchByDescription")
    public String searchByDescription(Model model, @ModelAttribute BookSearchRequest request) {

        Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByDescription(request);

        model.addAttribute("bookIndexList", bookIndexList);

        return "";

    }

    @PostMapping("/searchByTagName")
    public String searchByTagName(Model model, @ModelAttribute BookSearchRequest request) {

        Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByTagName(request);

        model.addAttribute("bookIndexList", bookIndexList);

        return "";

    }

    @PostMapping("/searchByAuthorName")
    public String searchByAuthorName(Model model, @ModelAttribute BookSearchRequest request) {

        Page<BookIndexResponse> bookIndexList = bookSearchAdaptor.searchByAuthorName(request);

        model.addAttribute("booxIndexList", bookIndexList);

        return "";

    }
}
