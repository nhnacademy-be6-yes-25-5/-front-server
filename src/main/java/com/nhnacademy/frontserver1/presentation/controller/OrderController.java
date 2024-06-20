package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.OrderService;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreateOrderRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.order.FindProductRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreateOrderResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadShippingPolicyResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadTakeoutResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("checkout")
    public String findAllCheckout(Model model, Pageable pageable) {
        List<FindProductRequest> requests = new ArrayList<>();
        requests.add(new FindProductRequest(3L, 30, BigDecimal.valueOf(1000)));
        requests.add(new FindProductRequest(4L, 1, BigDecimal.valueOf(5000)));

        Integer totalAmount = getTotalAmount(requests);
        ReadShippingPolicyResponse shippingPolicy = orderService.findAllOrderPolicy(pageable, totalAmount);
        ReadShippingPolicyResponse freeShippingPolicy = orderService.findFreePolicy();
        Integer freeShippingAmount = freeShippingPolicy.shippingPolicyMinAmount() - totalAmount;
        List<ReadTakeoutResponse> takeoutResponses = orderService.findAllTakeout();

        model.addAttribute("shippingPolicy", shippingPolicy);
        model.addAttribute("freeShippingPolicy", freeShippingPolicy);
        model.addAttribute("freeShippingAmount", freeShippingAmount);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("requests", requests);
        model.addAttribute("takeouts", takeoutResponses);

        return "order/checkout";
    }

    private Integer getTotalAmount(List<FindProductRequest> requests) {
        BigDecimal totalAmount = requests.stream()
            .map(request -> request.price().multiply(BigDecimal.valueOf(request.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAmount.intValue();
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@RequestBody CreateOrderRequest request, HttpSession session) {
        log.info("주문 요청이 들어왔습니다. {}", request.toString());
        Long userId = 1L;
        CreateOrderResponse response = orderService.createPreOrder(request, userId);

        session.setAttribute("orderId", response.orderId());
        session.setAttribute("totalAmount", response.totalAmount());
        session.setAttribute("bookIds", response.bookIds());
        session.setAttribute("quantities", response.quantities());

        return ResponseEntity.ok(response);
    }

}
