package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.BookService;
import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.application.service.TagService;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateBookRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookAPIResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.book.TagResponse;
import jakarta.validation.Valid;
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
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;
    private final TagService tagService;

    @GetMapping("/admin/product")
    public String adminProduct(Model model, @PageableDefault(page = 0, size = 10) Pageable pageable) {

        List<CategoryResponse> categoryList = categoryService.findAllCategories();
        List<TagResponse> tagList = tagService.findAllTags();
        Page<BookResponse> bookResponseList = bookService.findAllBooks(pageable);

        int nowPage = bookResponseList.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, bookResponseList.getTotalPages());

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("productList", bookResponseList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "admin/product/admin-product";
    }

    @PostMapping("/admin/product")
    public String adminProduct(@ModelAttribute @Valid CreateBookRequest request, @RequestParam(value = "categoryIdList") List<Long> categoryIdList, @RequestParam(value = "tagIdList", required = false) List<Long> tagIdList) {

        bookService.createBook(request, categoryIdList, tagIdList);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{bookId}/delete")
    public String adminDeleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return "redirect:/admin/product";
    }

    @PostMapping("/admin/product/{bookId}/update")
    public String adminUpdateBook(@ModelAttribute @Valid UpdateBookRequest request, @RequestParam(value = "categoryIdList") List<Long> categoryIdList, @RequestParam(value = "tagIdList", required = false) List<Long> tagIdList) {

        bookService.updateBook(request, categoryIdList, tagIdList);

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
}
