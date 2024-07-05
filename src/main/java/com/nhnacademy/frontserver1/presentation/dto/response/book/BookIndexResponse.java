package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record BookIndexResponse(
        String bookId,
        String bookIsbn,
        String bookName,
        String bookDescription,
        String bookPublisher,
        BigDecimal bookPrice,
        BigDecimal bookSellingPrice,
        String bookImage,
        Integer quantity,
        Integer reviewCount,
        Integer hitsCount,
        Integer searchCount,
        boolean bookIsPackable,
        List<String> authors,
        List<String> tags
)
{

}
