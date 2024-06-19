package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.util.List;

public record CreateOrderResponse(String orderId, Integer totalAmount, List<Long> bookIds, List<Integer> quantities) {

}
