package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;

public record CreatePaymentResponse(String orderId, BigDecimal amount) {

}
