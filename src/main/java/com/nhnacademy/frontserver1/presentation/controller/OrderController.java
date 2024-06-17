package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.presentation.dto.request.order.FindProductRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
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
        List<FindProductRequest> requests = new ArrayList<>();
        requests.add(new FindProductRequest(1L, 2, BigDecimal.valueOf(10000)));
        requests.add(new FindProductRequest(2L, 5, BigDecimal.valueOf(50000)));

        model.addAttribute("requests", requests);

        return "order/checkout";
    }

    @PostMapping
    public String createOrders(@ModelAttribute CreateOrderRequest request) {
        log.info("주문 요청이 들어왔습니다. {}", request.toString());
        Long userId = 1L;
        CreateOrderResponse response = orderService.createPreOrder(request, userId);

        return "redirect:/payments/" + request.orderId();
    }

}
