package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryResponse(
        Long categoryId,
        String categoryName,
        Long parentCategoryId,
        List<CategoryResponse> subCategories)
{

}
