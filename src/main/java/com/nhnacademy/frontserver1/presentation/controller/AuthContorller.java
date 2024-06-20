package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.HttpCookie;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthContorller {

    private final AuthServiceImpl authService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {

        String tokenKey = authService.loginUser(LoginUserRequest.builder().email(username).password(password).build());

        HttpCookie cookie = new HttpCookie("tokenKey", tokenKey);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", cookie.toString());

        return "index";
    }
}
