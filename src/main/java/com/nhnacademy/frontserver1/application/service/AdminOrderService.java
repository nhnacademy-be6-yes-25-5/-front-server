package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.request.order.CancelOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.CancelOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderCancelStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {

    Page<ReadAllUserOrderStatusResponse> getAllUserOrderStatus(Pageable pageable, String role);

    void updateOrderStatusByOrderId(String orderId,
        UpdateOrderStatusRequest updateOrderStatusRequest);

    Page<ReadAllUserOrderCancelStatusResponse> getAllUserOrderCancelStatus(Pageable pageable);

    CancelOrderResponse cancelOrder(String orderId, CancelOrderRequest request);
}
