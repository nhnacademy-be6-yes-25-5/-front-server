package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.domain.PaymentProvider;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;

public record UpdateOrderRequest(OrderStatusType orderStatusType, PaymentProvider paymentProvider) {

}
