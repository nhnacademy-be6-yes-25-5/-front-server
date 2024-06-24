package com.nhnacademy.frontserver1.presentation.dto.response.order;

public record ReadPaymentOrderResponse(Long bookId,
                                       String bookName,
                                       String bookAuthor,
                                       Integer bookPrice,
                                       String bookImage,
                                       Integer bookQuantity) {

}
