package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.presentation.dto.request.order.request.CreateOrderRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("checkout")
    public String checkout(Model model) {
        List<Long> productIds = List.of(1L, 2L);
        model.addAttribute("productIds", productIds);

        return "order/checkout";
    }

    // todo. 주문 정보 다시 맞추기. 주문자명, 이메일, 번호, 쿠폰 타입 추가
    @PostMapping
    public String createOrders(@ModelAttribute CreateOrderRequest createOrderRequest,
        @RequestParam List<Long> productIds) {
        System.out.println(createOrderRequest.toString());

        return "redirect:/payments";
    }

}
