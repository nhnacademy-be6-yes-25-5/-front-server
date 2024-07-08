package com.nhnacademy.frontserver1.presentation.dto.response.review;

import java.time.LocalDate;
import java.util.List;

public record ReadReviewResponse(Long reviewId,
                                 String title,
                                 String content,
                                 Integer rating,
                                 LocalDate reviewTime,
                                 String userName,
                                 Long userId,
                                 List<String> reviewImageUrls) {

}
