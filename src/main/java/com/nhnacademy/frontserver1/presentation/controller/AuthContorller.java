package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.common.utils.CookieUtils;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String loginForm(HttpServletRequest request) {
        CookieUtils.validateAccessToken(request);
        return "login";
    }



    /**
     * 인증 실패 로그인 페이질를 반환합니다.
     *
     * @return 인증 실패 로그인 페이지의 view 이름
     */
    @GetMapping("/error")
    public String showErrorPage(@RequestParam(required = false) String cause, Model model) {
        if (cause != null) {
            model.addAttribute("cause", cause);
        }
        return "error/auth-fail";
    }

    /**
     * 사용자 로그인을 처리합니다.
     *
     * @param loginUserRequest 로그인 요청 정보 (이메일과 비밀번호 포함)
     * @return 로그인 성공 시 메인 페이지로의 리다이렉트 경로
     */
    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserRequest loginUserRequest) {

        authService.loginUser(loginUserRequest);

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