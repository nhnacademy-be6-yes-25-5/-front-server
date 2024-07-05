package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/admin/category")
    public String adminCategory(Model model,@PageableDefault(page = 0, size = 10) Pageable pageable) {

        List<CategoryResponse> rootCategories = categoryService.findRootCategories();

        Page<CategoryResponse> categoryList =  categoryService.findAllCategories(pageable);
        int nowPage = categoryList.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 4, 0);
        int endPage = Math.min(nowPage + 5, categoryList.getTotalPages() -1);

        if(categoryList.getTotalPages() <= 10) {
            startPage = 0;
            endPage = categoryList.getTotalPages() - 1;
        } else {
            if (startPage == 0) {
                endPage = 9;
            } else if (endPage == categoryList.getTotalPages() -1) {
                startPage = categoryList.getTotalPages() - 10;
            }
        }

        model.addAttribute("categories", rootCategories);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("nowPage", nowPage + 1);
        model.addAttribute("startPage", startPage + 1);
        model.addAttribute("endPage", endPage + 1);

        return "admin/category/admin-category";
    }

    @GetMapping("/admin/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return "redirect:/admin/category";
    }

    @PostMapping("/admin/category")
    public String addCategory(@ModelAttribute @Valid CreateCategoryRequest request , BindingResult bindingResult) {

//        if(bindingResult.hasErrors()) {
//            return "404";
//        }

        categoryService.createCategory(request);

        return "redirect:/admin/category";
    }

    @PostMapping("/admin/category/update")
    public String updateCategory(@ModelAttribute @Valid UpdateCategoryRequest request, BindingResult bindingResult) {

//        if(bindingResult.hasErrors()) {
//            return "404";
//        }

        categoryService.updateCategory(request);

        return "redirect:/admin/category";
    }
}
