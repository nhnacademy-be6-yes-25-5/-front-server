package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpHeaders;

/**
 * AuthController 클래스는 사용자 인증 관련 기능을 제공하는 Spring MVC 컨트롤러입니다.
 * 이 클래스는 로그인, 로그아웃 및 토큰 테스트 기능을 구현합니다.
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
     * @param loginUserRequest 로그인 요청 정보 (이메일과 비밀번호 포함)
     * @param response HTTP 응답 객체
     * @return 로그인 성공 시 메인 페이지로의 리다이렉트 경로
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse response) {
        AuthResponse token = authService.loginUser(loginUserRequest);

        // AccessToken을 헤더에 추가
        Cookie accessTokenCookie = new Cookie("AccessToken", token.accessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true); // HTTPS 연결에서만 전송
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("RefreshToken", token.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 연결에서만 전송
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:/";
    }


    /**
     * 토큰 테스트 페이지를 반환합니다.
     * AuthServiceImpl의 testToken() 메서드를 호출하여 테스트 결과(customerId)를 가져오고,
     * 이를 모델에 추가하여 뷰에 전달합니다.
     *
     * @param model Spring MVC 모델 객체
     * @return 토큰 테스트 페이지의 view 이름
     */
    @GetMapping("/test")
    public String test(Model model) {

        String returnResponse = authService.testToken();
        model.addAttribute("returnResponse", returnResponse);

        return "tokenTest";
    }

}