package com.nhnacademy.frontserver1.application.service;

import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminOrderService {

    Page<ReadAllUserOrderStatusResponse> getAllUserOrderStatus(Pageable pageable);

    void updateOrderStatusByOrderId(String orderId,
        UpdateOrderStatusRequest updateOrderStatusRequest);
}
