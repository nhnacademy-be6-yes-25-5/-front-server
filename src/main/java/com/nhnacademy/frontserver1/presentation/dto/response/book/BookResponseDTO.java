package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;
/*
    //도서명 주고 도서 정보 반환하는 코드
 */
@Builder
public record BookResponseDTO(
        Long bookId,
        String bookName,
        String authorName,
        String bookPublisher
) {}