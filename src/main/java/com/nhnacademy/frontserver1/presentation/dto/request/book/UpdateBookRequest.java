package com.nhnacademy.frontserver1.presentation.dto.request.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Builder
public record UpdateBookRequest(

        @NotNull(message = "책 아이디는 필수 입력 항목입니다.")
        Long bookId,

        @NotBlank(message = "책 ISBN은 필수 입력 항목입니다.")
        @Size(min=10, max = 13, message = "ISBN의 사이즈는 10이거나 13입니다.")
        String bookIsbn,

        @NotBlank(message = "책 이름은 필수 입력 항목입니다.")
        String bookName,

        String bookDescription,

        String index,

        String bookAuthor,

        String bookPublisher,

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @PastOrPresent(message = "책 출간일은 미래일 수 없습니다.")
        Date bookPublishDate,

        @NotNull(message = "책 가격은 필수 입력 항목입니다.")
        BigDecimal bookPrice,

        @NotNull(message = "책 판매 가격은 필수 입력 항목입니다.")
        BigDecimal bookSellingPrice,

        String imageURL,

        @NotNull(message = "책 수량은 필수 입력 항목입니다.")
        Integer quantity,

        boolean bookIsPackable
) {
    public static UpdateBookRequest updateImageURL(UpdateBookRequest request, String imageURL) {
            return UpdateBookRequest.builder()
                    .bookId(request.bookId())
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
