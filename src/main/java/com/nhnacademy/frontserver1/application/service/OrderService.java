package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    CreateOrderResponse createPreOrder(CreateOrderRequest request, Long userId);

    ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable, Integer totalAmount);

    ReadShippingPolicyResponse findFreePolicy();
}
