package com.nhnacademy.frontserver1.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
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

    @GetMapping
    public String payment(){
        return "order/toss";
    }


    @PostMapping("confirm")
    public void confirm(@RequestBody CreatePaymentRequest request) {
        CreatePaymentResponse response = paymentService.createPayment(request);
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
