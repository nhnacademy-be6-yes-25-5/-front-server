package com.nhnacademy.frontserver1.presentation.dto.response.book;

import lombok.Builder;

@Builder
public record UploadImageResponse(
        String imageUrl,
        String name,
        String path
)
{

}
