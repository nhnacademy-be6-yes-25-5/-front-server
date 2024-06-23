package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CategoryService {

    Page<CategoryResponse> findAllCategories(Pageable pageable);

    List<CategoryResponse> findAllCategories();

    CategoryResponse findCategoryById(@PathVariable("categoryId") Long categoryId);

    List<CategoryResponse> findRootCategories();

    List<CategoryResponse> findCategoriesByParentId(@PathVariable("parentId") Long parentId);

    CategoryResponse createCategory(@RequestBody CreateCategoryRequest request);

    CategoryResponse updateCategory(@RequestBody UpdateCategoryRequest request);

    void deleteCategory(@PathVariable("categoryId") Long categoryId);

}
