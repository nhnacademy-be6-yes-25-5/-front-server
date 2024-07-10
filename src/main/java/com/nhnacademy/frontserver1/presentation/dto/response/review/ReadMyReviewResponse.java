package com.nhnacademy.frontserver1.presentation.dto.response.review;

import java.time.LocalDate;

public record ReadMyReviewResponse(LocalDate createdAt,
                                   String title,
                                   String content,
                                   Integer rating,
                                   String bookName) {

}
