package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.presentation.dto.request.order.request.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("checkout")
    public String checkout(Model model) {
        List<Long> productIds = List.of(1L, 2L);
        model.addAttribute("productIds", productIds);

        return "order/checkout";
    }

    // todo. 쿠폰 및 배송비 받기 추가
    @PostMapping
    public String createOrders(@ModelAttribute CreateOrderRequest request,
        @RequestParam List<Long> productIds) {
        log.info("주문 요청이 들어왔습니다. {} {}", request.toString(), productIds);
        Long userId = 1L;
        CreateOrderResponse response = orderService.createPreOrder(request, userId);

        return "redirect:/payments";
    }

}
