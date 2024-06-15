package com.nhnacademy.frontserver1.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping
    public String payment(){
        return "order/toss";
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
