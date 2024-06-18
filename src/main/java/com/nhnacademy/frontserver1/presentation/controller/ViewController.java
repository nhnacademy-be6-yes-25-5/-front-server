package com.nhnacademy.frontserver1.presentation.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Value("${server.port}")
    private String port;

    @GetMapping("view")
    public String index(){
        return "admin/admin-dashboard";
    }
}
