package com.nhnacademy.frontserver1.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/toss")
    public String checkout(){
        return "toss";
    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping("/fail")
    public String fail(){
        return "fail";
    }
}
