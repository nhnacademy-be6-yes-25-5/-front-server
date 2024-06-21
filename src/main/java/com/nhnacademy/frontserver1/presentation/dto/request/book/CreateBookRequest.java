package com.nhnacademy.frontserver1.presentation.dto.request.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record CreateBookRequest (
        @NotNull(message = "ISBN이 없습니다.")
        @Size(min = 10, max = 13)
        String bookIsbn,

        @NotBlank(message = "책 제목은 필수 입력항목입니다.")
        String bookName,

        String bookDescription,

        String index,

        String bookAuthor,

        String bookPublisher,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PastOrPresent(message = "출판 일자는 미래일 수 없습니다.")
        Date bookPublishDate,

        @NotNull(message = "책 가격은 필수 입력 항목입니다.")
        BigDecimal bookPrice,

        @NotNull(message = "책 판매 가격은 필수 입력 항목입니다.")
        BigDecimal bookSellingPrice,

        String bookImage,

        @NotNull(message = "수량은 필수 입력 항목입니다.")
        Integer quantity
)
{
}

