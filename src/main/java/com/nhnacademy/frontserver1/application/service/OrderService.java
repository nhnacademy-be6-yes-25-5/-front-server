package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    CreateOrderResponse createPreOrder(CreateOrderRequest request);

    ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable, Integer totalAmount);

    ReadShippingPolicyResponse findFreePolicy();

    List<ReadTakeoutResponse> findAllTakeout();

    List<ReadCartBookResponse> findAllCartBok(Long userId);
}
