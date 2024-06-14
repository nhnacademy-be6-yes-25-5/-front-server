package com.nhnacademy.frontserver1.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${server.port}")
    private String port;

    @GetMapping
    public String index(HttpServletRequest request, Model model){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null ){
            ip = request.getRemoteAddr();
        } 
        
        model.addAttribute("ip",ip);
        model.addAttribute("port",port);
        return "index";
    }
}
