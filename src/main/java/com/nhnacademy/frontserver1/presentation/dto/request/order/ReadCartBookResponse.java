package com.nhnacademy.frontserver1.presentation.dto.request.order;

import java.math.BigDecimal;

public record ReadCartBookResponse(Long cartBookId,
                                   Long bookId,
                                   String bookName,
                                   BigDecimal bookPrice,
                                   int cartBookQuantity,
                                   Long userId) {

    public static ReadCartBookResponse from() {
        return new ReadCartBookResponse(6L, 1L, "멋진책", BigDecimal.valueOf(10000), 1, 1L);
    }
}
