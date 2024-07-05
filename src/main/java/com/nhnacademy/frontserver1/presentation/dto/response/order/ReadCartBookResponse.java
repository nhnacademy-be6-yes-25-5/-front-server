package com.nhnacademy.frontserver1.presentation.dto.response.order;

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
                                   Boolean bookIsPackable,
                                   String bookImage) {

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

    public static ReadCartBookResponse of(ReadOrderBookInfoResponse orderBookInfoResponse, Integer quantity) {
        return ReadCartBookResponse.builder()
            .bookId(orderBookInfoResponse.bookId())
            .bookName(orderBookInfoResponse.bookName())
            .bookPrice(orderBookInfoResponse.bookPrice())
            .cartBookQuantity(quantity)
            .bookIsPackable(orderBookInfoResponse.bookIsPackable())
            .bookImage(orderBookInfoResponse.bookImage())
            .build();
    }
}
