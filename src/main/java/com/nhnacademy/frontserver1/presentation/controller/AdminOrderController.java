package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.AdminOrderService;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminOrderController {

    private final AdminOrderService adminOrderservice;

    @GetMapping("/orders")
    public String getAdminOrderStatusPage(Pageable pageable, Model model) {
        Page<ReadAllUserOrderStatusResponse> responses = adminOrderservice.getAllUserOrderStatus(pageable);

        model.addAttribute("orderInfos", responses);
        return "admin/order/admin-order-status";
    }

    @GetMapping("/orders/cancel")
    public String getAdminOrderCancelPage(Model model) {
        return "admin/order/admin-cancel-status";
    }

    @GetMapping("/orders/refund")
    public String getAdminOrderRefundPage(Model model) {
        return "admin/order/admin-refund-status";
    }
}
