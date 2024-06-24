package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.CartAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.OrderAdaptor;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PolicyAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.ReadCartBookResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderAdaptor orderAdaptor;
    private final PolicyAdaptor policyAdaptor;
    private final CartAdaptor cartAdaptor;

    @Override
    public CreateOrderResponse createPreOrder(CreateOrderRequest request) {
        return orderAdaptor.createPreOrder(request);
    }

    @Override
    public ReadShippingPolicyResponse findAllOrderPolicy(Pageable pageable,
        Integer totalAmount) {
        Page<ReadShippingPolicyResponse> policyResponses = policyAdaptor.findAllDeliveryPolicy(pageable);

        return policyResponses.getContent().stream()
            .filter(policy -> totalAmount >= policy.shippingPolicyMinAmount())
            .min(Comparator.comparingInt(ReadShippingPolicyResponse::shippingPolicyFee))
            .orElse(null);
    }

    @Override
    public ReadShippingPolicyResponse findFreePolicy() {
        return policyAdaptor.findFreePolicy();
    }

    @Override
    public List<ReadTakeoutResponse> findAllTakeout() {
        return policyAdaptor.findAllTakeoutPolicy();
    }

    @Override
    public List<ReadCartBookResponse> findAllCartBok(Long userId) {
        return cartAdaptor.getCartBooks(userId);
    }

    @Override
    public List<ReadPaymentOrderResponse> findAllOrderByOrderId(String orderId) {
        return orderAdaptor.findAllByOrderId(orderId);
    }

    @Override
    public ReadOrderStatusResponse getOrderStatusByOrderId(String orderId) {
        return orderAdaptor.findOrderStatusByOrderId(orderId);
    }
}
