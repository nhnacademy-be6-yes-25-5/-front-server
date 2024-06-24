package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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

    private final AuthService authService;


    @GetMapping("view")
    public String index(Model model){
        String returnMent = authService.testToken("test");
        model.addAttribute("returnMent", returnMent);
        return "tokenTest";
    }
}
