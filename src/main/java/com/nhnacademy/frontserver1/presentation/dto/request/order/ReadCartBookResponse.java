package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.presentation.dto.response.book.BookResponse;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ReadCartBookResponse(Long cartBookId,
                                   Long bookId,
                                   String bookName,
                                   BigDecimal bookPrice,
                                   int cartBookQuantity,
                                   Long userId,
                                   Boolean bookIsPackable) {

    public static ReadCartBookResponse from(BookResponse bookResponse, Integer quantity) {
        return ReadCartBookResponse.builder()
            .bookId(bookResponse.bookId())
            .bookName(bookResponse.bookName())
            .bookPrice(bookResponse.bookPrice())
            .cartBookQuantity(quantity)
            .cartBookQuantity(1)
            .bookIsPackable(bookResponse.bookIsPackable())
            .build();
    }
}
