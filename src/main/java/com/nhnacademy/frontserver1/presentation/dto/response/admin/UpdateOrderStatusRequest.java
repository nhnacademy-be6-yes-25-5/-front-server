package com.nhnacademy.frontserver1.presentation.dto.response.admin;

import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;

public record UpdateOrderStatusRequest(OrderStatusType orderStatusType) {

}
