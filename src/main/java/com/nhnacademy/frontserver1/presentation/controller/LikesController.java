package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.common.exception.LikesNotLoginException;
import com.nhnacademy.frontserver1.infrastructure.adaptor.LikesAdapter;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.LikesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesAdapter likesAdapter;
    private final BookService bookService;

    @GetMapping("/likesClick/{bookId}")
    public String likesClick(@PathVariable Long bookId) {

        likesAdapter.click(bookId);

        return "redirect:/detail/" + bookId;
    }

    @GetMapping("/likesList")
    public String likesList(Model model) {

        List<LikesResponse> likesList = likesAdapter.findLikesByUserId().getBody();
        List<BookResponse> bookList = new ArrayList<>();

        for(LikesResponse like : likesList) {

            BookResponse book = bookService.getBook(like.bookId());

            bookList.add(book);
        }

        model.addAttribute("bookList", bookList);

        return "mypage/mypage-likes";
    }

    @ExceptionHandler(LikesNotLoginException.class)
    public String likesNotLoginException(Model model) {

        model.addAttribute("message", "회원가입이 필요합니다.");
        model.addAttribute("url", "/");

        return "message";
    }
}
