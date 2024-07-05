package com.nhnacademy.frontserver1.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    // 이메일찾기실패시 링크누르면이동
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // register.html 뷰를 반환
    }
}
