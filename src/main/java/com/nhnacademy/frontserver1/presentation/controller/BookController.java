package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.LikesAdapter;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {

    private final BookService bookService;
    private final LikesAdapter likesAdapter;
    private final CategoryService categoryService;

    @GetMapping("/detail/{bookId}")
    public String detail(Model model, @PathVariable Long bookId) {

        BookResponse book = bookService.getBook(bookId);
        List<CategoryResponse> rootCategories = categoryService.findRootCategories();

        if(likesAdapter.exist(bookId)) {
            model.addAttribute("like", likesAdapter.findByBookIdAndUserId(bookId));
        }

        model.addAttribute("book", book);
        model.addAttribute("categories", rootCategories);

        return "product/product-details";
    }

}
