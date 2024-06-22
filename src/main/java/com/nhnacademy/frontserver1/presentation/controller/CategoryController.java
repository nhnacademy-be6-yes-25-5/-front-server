package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String adminCategory(Model model) {

        List<CategoryResponse> categoryList =  categoryService.findAllCategories();

        model.addAttribute("categoryList", categoryList);

        return "admin/category/admin-category";
    }

    @GetMapping("/admin/category/{categoryId}/delete")
    public String deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return "redirect:/admin/category";
    }

    @PostMapping("/admin/category")
    public String addCategory(@ModelAttribute CreateCategoryRequest request) {

        categoryService.createCategory(request);

        return "redirect:/admin/category";
    }

    @PostMapping("/admin/category/update")
    public String updateCategory(@ModelAttribute UpdateCategoryRequest request) {

        categoryService.updateCategory(request);

        return "redirect:/admin/category";
    }
}
