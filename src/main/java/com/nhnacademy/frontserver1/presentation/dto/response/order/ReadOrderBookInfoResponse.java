package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;

public record ReadOrderBookInfoResponse(Long bookId,
                                        String bookName,
                                        BigDecimal bookPrice,
                                        Boolean bookIsPackable,
                                        String bookImage) {
}
