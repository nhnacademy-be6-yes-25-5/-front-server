package com.nhnacademy.frontserver1.presentation.dto.request.book;

import jakarta.validation.constraints.NotBlank;

public record CreateTagRequest(

        @NotBlank(message = "태그 이름은 필수 입력 항목입니다.")
        String tagName
) {
}
