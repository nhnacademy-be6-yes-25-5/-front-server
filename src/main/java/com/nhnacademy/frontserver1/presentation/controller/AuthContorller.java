package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.common.provider.JwtTokenProvider;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private final JwtTokenProvider jwtTokenProvider;

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
     * 2. 로그인 성공 시 토큰을 반환하고, 이를 쿠키에 저장하여 응답합니다.
     * 3. 인증 성공 후 메인 페이지로 리다이렉트합니다.
     *
     * @param loginUserRequest 로그인 요청 정보
     * @param response 응답 객체
     * @return 메인 페이지로의 리다이렉트 경로
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse response) {
        String token = authService.loginUser(loginUserRequest);

        Cookie authCookie = new Cookie("Authorization", token);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        response.addCookie(authCookie);

        return "redirect:/";
    }

    /**
     * 토큰 테스트 페이지를 반환합니다.
     *
     * 1. 요청 헤더에서 Authorization 헤더 값을 가져옵니다.
     * 2. AuthServiceImpl의 testToken() 메서드를 호출하여 테스트 결과를 가져옵니다.
     * 3. 테스트 결과를 모델에 추가하여 뷰에 전달합니다.
     *
     * @param model 모델 객체
     * @param request 요청 객체
     * @return 토큰 테스트 페이지의 view 이름
     */
    @GetMapping("/test")
    public String test(Model model, HttpServletRequest request) {
        String token = jwtTokenProvider.getTokenFromCookie(request);

        if (token != null) {
            String returnResponse = authService.testToken(token);
            model.addAttribute("returnResponse", returnResponse);
        }
        return "tokenTest";
    }

}