package com.nhnacademy.frontserver1.presentation.dto.request.order;

import java.math.BigDecimal;

public record ReadCartBookResponse(Long cartBookId,
                                   Long bookId,
                                   String bookName,
                                   BigDecimal bookPrice,
                                   int cartBookQuantity) {

}
