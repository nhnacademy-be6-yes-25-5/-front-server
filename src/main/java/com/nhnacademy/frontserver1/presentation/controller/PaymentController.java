package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public String confirm(@RequestBody CreatePaymentRequest request, HttpSession session) {
        String orderId = (String) session.getAttribute("orderId");
        Integer totalAmount = (Integer) session.getAttribute("totalAmount");

        if (!Objects.equals(request.orderId(), orderId) || !Objects.equals(request.amount(), totalAmount)) {
            return "redirect:/payments/fail";
        }

        if (paymentService.createPayment(request).status() != 200) {
            return "order/fail";
        }

        session.removeAttribute("orderId");
        session.removeAttribute("totalAmount");
        session.removeAttribute("customerKey");

        return "order/success";
    }

    @GetMapping("/success")
    public String success(){
        return "order/success";
    }

    @GetMapping("/fail")
    public String fail(){
        return "order/fail";
    }
}
