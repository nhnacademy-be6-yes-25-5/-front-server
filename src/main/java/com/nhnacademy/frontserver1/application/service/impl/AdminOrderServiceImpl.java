package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.AdminOrderService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.AdminOrderAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AdminOrderAdaptor adminOrderAdaptor;

    @Override
    public Page<ReadAllUserOrderStatusResponse> getAllUserOrderStatus(Pageable pageable) {
        return adminOrderAdaptor.getAllUserOrderStatusByPaging(pageable);
    }
}
