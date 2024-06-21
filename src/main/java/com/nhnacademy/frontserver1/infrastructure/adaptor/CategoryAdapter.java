package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "categoryAdapter", url = "http://localhost:8085/categories")
public interface CategoryAdapter {

    @GetMapping
    List<CategoryResponse> findAllCategories();

    @GetMapping("/{categoryId}")
    CategoryResponse findCategoryById(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/root")
    List<CategoryResponse> findRootCategories();

    @GetMapping("/parent/{parentId}")
    List<CategoryResponse> findCategoriesByParentId(@PathVariable("parentId") Long parentId);

    @PostMapping
    CategoryResponse createCategory(@RequestBody CreateCategoryRequest request);

    @PutMapping
    CategoryResponse updateCategory(@RequestBody UpdateCategoryRequest request);

    @DeleteMapping("/{categoryId}")
    void deleteCategory(@PathVariable("categoryId") Long categoryId);

}
