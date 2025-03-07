package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "categoryAdapter", url = "${eureka.gateway}/books/categories")
public interface CategoryAdapter {

    @GetMapping("/page")
    Page<CategoryResponse> findAllCategories(Pageable pageable);

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
