package com.nhnacademy.frontserver1.presentation.dto.response.book;

public record LikesResponse (Long likesId, Long bookId, Long userId, boolean likesStatus) {
}
