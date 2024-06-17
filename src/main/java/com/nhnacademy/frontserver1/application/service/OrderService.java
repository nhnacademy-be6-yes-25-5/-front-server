package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.request.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;

public interface OrderService {

    CreateOrderResponse createPreOrder(CreateOrderRequest request, Long userId);
}
