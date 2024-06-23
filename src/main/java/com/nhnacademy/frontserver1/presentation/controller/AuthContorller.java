package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.HttpCookie;

/**
 * AuthController 클래스는 사용자 인증 관련 기능을 제공하는 Spring MVC 컨트롤러입니다.
 * 이 클래스는 로그인 페이지 및 로그인 기능을 구현합니다.
 *
 * @author lettuce82
 * @version 1.0
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthContorller {

    private final AuthServiceImpl authService;

    /**
     * 로그인 페이지를 반환합니다.
     *
     * @return 로그인 페이지의 view 이름
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 사용자 로그인을 처리합니다.
     *
     * 1. 사용자의 이메일과 비밀번호를 받아 AuthServiceImpl을 통해 로그인을 수행합니다.
     * 2. 로그인 성공 시 레디스에 토큰 저장할 때 사용된 토큰 키를 반환하고, 이를 쿠키에 저장하여 응답 헤더에 추가합니다.
     * 3. 인증 성공 후 메인 페이지로 리다이렉트합니다.
     *
     * @param username 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 메인 페이지로의 리다이렉트 경로
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        String token = authService.loginUser(LoginUserRequest.builder().email(username).password(password).build());
        response.setHeader("Authorization", "Bearer " + token);
        return "index";
    }
}