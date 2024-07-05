package com.nhnacademy.frontserver1.presentation.dto.request.cart;

import lombok.Builder;

@Builder
public record UpdateCartBookRequest(int quantity) {
}
