package com.nhnacademy.frontserver1.presentation.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.BookAdapter;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookAPIResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping("/admin/product")
    public String adminProduct(Model model) {

        List<CategoryResponse> categoryList = categoryService.findAllCategories();
        List<BookResponse> bookResponseList = bookService.findAllBooks();

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productList", bookResponseList);


        return "admin/product/admin-product";
    }

    @PostMapping("/admin/product")
    public String adminProduct(@ModelAttribute CreateBookRequest request, @RequestParam(value = "categoryIdList") List<Long> categoryIdList, @RequestParam(value = "tagIdList", required = false) List<Long> tagIdList) {
        bookService.createBook(request, categoryIdList, tagIdList);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/bookSearch")
    public String adminBookSearch(Model model) {
        String keyword = "";
        model.addAttribute("keyword", keyword);

        return "admin/product/admin-book-search";
    }

    @PostMapping("/admin/bookSearch")
    public String adminBookSearch(@ModelAttribute("keyword") String keyword, Model model) {

        List<BookAPIResponse> bookList = bookService.searchBooks(keyword);
        model.addAttribute("bookList", bookList);

        return "admin/product/admin-book-search";
    }

    @GetMapping("admin/product/{bookId}/delete")
    public String adminDeleteBook(@PathVariable Long bookId, Model model) {

        bookService.deleteBook(bookId);

        return "redirect:/admin/product/" + bookId;
    }
}
