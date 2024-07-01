package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.AdminOrderService;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.ReadAllUserOrderStatusResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.UpdateOrderStatusRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
@Slf4j
public class AdminOrderController {

    private final AdminOrderService adminOrderservice;

    @GetMapping
    public String getAdminOrderStatusPage(Pageable pageable, Model model, @RequestParam(required = false) String role) {
        Page<ReadAllUserOrderStatusResponse> responses = adminOrderservice.getAllUserOrderStatus(pageable, role);

        model.addAttribute("role", role);
        model.addAttribute("orderInfos", responses);

        return "admin/order/admin-order-status";
    }

    @PutMapping("/{orderId}")
    public String updateOrderStatus(@PathVariable String orderId, @ModelAttribute UpdateOrderStatusRequest updateOrderStatusRequest) {
        log.info("관리자로부터 배송정보 수정 요청이 들어왔습니다.");
        adminOrderservice.updateOrderStatusByOrderId(orderId, updateOrderStatusRequest);

        return "redirect:/admin/orders";
    }

    @GetMapping("/cancel")
    public String getAdminOrderCancelPage(Model model) {
        return "admin/order/admin-cancel-status";
    }

    @GetMapping("/refund")
    public String getAdminOrderRefundPage(Model model) {
        return "admin/order/admin-refund-status";
    }
}
