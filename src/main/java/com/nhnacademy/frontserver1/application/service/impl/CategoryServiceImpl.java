package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.CategoryService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CategoryAdapter;
import com.nhnacademy.frontserver1.presentation.dto.request.book.CreateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.book.UpdateCategoryRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.book.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdapter categoryAdapter;

    @Override
    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        return categoryAdapter.findAllCategories(pageable);
    }

    @Override
    public List<CategoryResponse> findAllCategories() {
        return categoryAdapter.findAllCategories();
    }

    @Override
    public CategoryResponse findCategoryById(Long categoryId) {
        return categoryAdapter.findCategoryById(categoryId);
    }

    @Override
    public List<CategoryResponse> findRootCategories() {
        return List.of();
    }

    @Override
    public List<CategoryResponse> findCategoriesByParentId(Long parentId) {
        return List.of();
    }

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        return categoryAdapter.createCategory(request);
    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest request) {
        return categoryAdapter.updateCategory(request);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryAdapter.deleteCategory(categoryId);
    }
}
