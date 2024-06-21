package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> findAllCategories();

    CategoryResponse findCategoryById(@PathVariable("categoryId") Long categoryId);

    List<CategoryResponse> findRootCategories();

    List<CategoryResponse> findCategoriesByParentId(@PathVariable("parentId") Long parentId);

    CategoryResponse createCategory(@RequestBody CreateCategoryRequest request);

    CategoryResponse updateCategory(@RequestBody UpdateCategoryRequest request);

    void deleteCategory(@PathVariable("categoryId") Long categoryId);

}
