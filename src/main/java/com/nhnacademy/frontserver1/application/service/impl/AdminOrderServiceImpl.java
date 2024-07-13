package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AdminOrderService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AdminOrderAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CancelOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.CancelOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderCancelStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AdminOrderAdaptor adminOrderAdaptor;

    @Override
    public Page<ReadAllUserOrderStatusResponse> getAllUserOrderStatus(Pageable pageable,
        String role) {
        return adminOrderAdaptor.getAllUserOrderStatusByPaging(pageable, role).getBody();
    }

    @Override
    public void updateOrderStatusByOrderId(String orderId,
        UpdateOrderStatusRequest updateOrderStatusRequest) {
        adminOrderAdaptor.updateOrderStatusByOrderId(orderId, updateOrderStatusRequest).getBody();
    }

    @Override
    public Page<ReadAllUserOrderCancelStatusResponse> getAllUserOrderCancelStatus(Pageable pageable) {
        return adminOrderAdaptor.getAllUserOrderCancelStatusByPaging(pageable).getBody();
    }

    @Override
    public CancelOrderResponse cancelOrder(String orderId, CancelOrderRequest request) {
        return adminOrderAdaptor.cancelOrderByOrderId(orderId, request).getBody();
    }
}
