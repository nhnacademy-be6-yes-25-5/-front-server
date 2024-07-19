package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record ReadCartBookResponse(Long bookId,
                                   List<Long> categoryIds,
                                   String bookName,
                                   BigDecimal bookPrice,
                                   int cartBookQuantity,
                                   Boolean bookIsPackable,
                                   String bookImage) {

}
