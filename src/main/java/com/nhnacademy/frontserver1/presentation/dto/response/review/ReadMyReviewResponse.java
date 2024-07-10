package com.nhnacademy.frontserver1.presentation.dto.response.review;

import java.time.LocalDate;
import java.util.List;

public record ReadMyReviewResponse(Long reviewId,
                                   LocalDate createdAt,
                                   String title,
                                   String content,
                                   Integer rating,
                                   String bookName,
                                   List<String> reviewImages) {

}
