package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdapter;
import com.nhnacademy.frontserver1.presentation.dto.response.coupon.BookCouponResponse;
import com.nhnacademy.frontserver1.infrastructure.adaptor.LikesAdapter;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponseDTO;
import com.nhnacademy.frontserver1.presentation.dto.response.book.LikesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final BookAdapter bookAdapter;
    private final LikesAdapter likesAdapter;

    @GetMapping("/detail/{bookId}")
    public String detail(Model model, @PathVariable Long bookId) {

        BookResponse book = bookService.getBook(bookId);

        if(likesAdapter.exist(bookId)) {
            model.addAttribute("like", likesAdapter.findByBookIdAndUserId(bookId));
        }

        model.addAttribute("book", book);

        return "product/product-details";
    }

}
