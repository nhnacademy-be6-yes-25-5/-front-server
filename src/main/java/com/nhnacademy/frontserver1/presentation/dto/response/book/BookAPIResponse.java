package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookAPIResponse(
        String title,
        String author,
        String isbn,
        String imageURL,
        Integer price,
        String publisher,
        String description,
        LocalDateTime publishDate
) {
}
