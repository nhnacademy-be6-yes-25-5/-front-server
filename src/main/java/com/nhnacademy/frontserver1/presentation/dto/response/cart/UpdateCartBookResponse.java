package com.nhnacademy.frontserver1.presentation.dto.response.cart;

import lombok.Builder;

@Builder
public record UpdateCartBookResponse(Long cartBookId, int bookQuantity) {
}
