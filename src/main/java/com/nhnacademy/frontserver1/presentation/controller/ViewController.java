package com.nhnacademy.frontserver1.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ViewController {

    @Value("${server.port}")
    private String port;


    @GetMapping("view")
    public String index(Model model){
        return "admin/orderStatus/admin-order-status";
    }
}
