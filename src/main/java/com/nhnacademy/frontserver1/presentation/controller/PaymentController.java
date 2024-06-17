package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("{orderId}")
    public String payment(@PathVariable String orderId, Model model){
        model.addAttribute("orderId", orderId);

        return "order/toss";
    }

    @PostMapping("confirm")
    public String confirm(@RequestBody CreatePaymentRequest request) {
        try {
            paymentService.createPayment(request);
        } catch (Exception e) {
            return "order/fail";
        }

        return "order/success";
    }

    @GetMapping("success")
    public String success(){
        return "order/success";
    }

    @GetMapping("fail")
    public String fail(){
        return "order/fail";
    }
}
