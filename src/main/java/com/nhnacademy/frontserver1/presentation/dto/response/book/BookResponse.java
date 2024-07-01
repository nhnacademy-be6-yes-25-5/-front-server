package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record BookResponse(
        Long bookId,
        String bookIsbn,
       String bookName,
       String bookDescription,
       String bookAuthor,
       String bookIndex,
       String bookPublisher,
       Date bookPublishDate,
       BigDecimal bookPrice,
       BigDecimal bookSellingPrice,
       String bookImage,
       Integer bookQuantity,
       Integer reviewCount,
       Integer hitsCount,
       Integer searchCount
) {

}