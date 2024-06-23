package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

@Builder
public record CategoryResponse(Long categoryId, String categoryName, Long parentCategoryId) {
}
