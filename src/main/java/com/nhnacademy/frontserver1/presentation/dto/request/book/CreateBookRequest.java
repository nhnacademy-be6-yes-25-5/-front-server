package com.nhnacademy.frontserver1.presentation.dto.request.book;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Builder
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
        Date bookPublishDate,

        @NotNull(message = "책 가격은 필수 입력 항목입니다.")
        @Positive
        BigDecimal bookPrice,

        @Positive
        @NotNull(message = "책 판매 가격은 필수 입력 항목입니다.")
        BigDecimal bookSellingPrice,

        @Positive
        @NotNull(message = "수량은 필수 입력 항목입니다.")
        Integer quantity,

        String imageURL,

        boolean bookIsPackable
)
{
        public static CreateBookRequest updateImageURL(CreateBookRequest request, String imageURL) {
                return CreateBookRequest.builder()
                        .bookDescription(request.bookDescription())
                        .bookIsbn(request.bookIsbn())
                        .bookName(request.bookName())
                        .index(request.index())
                        .bookAuthor(request.bookAuthor())
                        .bookPublisher(request.bookPublisher())
                        .bookPublishDate(request.bookPublishDate())
                        .bookPrice(request.bookPrice())
                        .bookSellingPrice(request.bookSellingPrice())
                        .quantity(request.quantity())
                        .imageURL(imageURL)
                        .bookIsPackable(request.bookIsPackable())
                        .build();
        }
}

