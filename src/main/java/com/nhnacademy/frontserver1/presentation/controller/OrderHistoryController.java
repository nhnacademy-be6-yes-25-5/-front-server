package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMyOrderHistoryResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDeliveryInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderDetailResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPurePriceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class OrderHistoryController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String getMyPageOrders(Pageable pageable, Model model) {
        Page<ReadMyOrderHistoryResponse> readMyOrderHistoryResponses = orderService.getMyOrders(pageable);
        ReadPurePriceResponse purePriceResponse = orderService.getPurePrice();

        model.addAttribute("purePrice", purePriceResponse.purePrice());
        model.addAttribute("orders", readMyOrderHistoryResponses);

        return "mypage/mypage-orders-history";
    }

    @GetMapping("/orders/{orderId}")
    public String getOrder(@PathVariable String orderId, Model model) {
        ReadOrderDetailResponse response = orderService.getMyOrderByOrderId(orderId);
        model.addAttribute("order", response);

        return "mypage/mypage-orders-detail";
    }

    @GetMapping("/orders/{orderId}/delivery")
    public String getOrderDelivery(@PathVariable String orderId, Model model) {
        ReadOrderDeliveryInfoResponse orderInfoResponse = orderService.getMyOrderDelivery(orderId);
        model.addAttribute("orderInfo", orderInfoResponse);

        return "mypage/mypage-delivery-detail";
    }
}
