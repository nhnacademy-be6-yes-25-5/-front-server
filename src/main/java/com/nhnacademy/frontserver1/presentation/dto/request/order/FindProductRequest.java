package com.nhnacademy.frontserver1.presentation.dto.request.order;

import java.math.BigDecimal;

public record FindProductRequest(Long productId, int quantity, BigDecimal price) {

}
