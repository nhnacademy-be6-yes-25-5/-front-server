package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.UserServiceImpl;
import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.nhnacademy.frontserver1.application.service.impl.PaycoServiceImpl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse.Member;

@Controller
@RequiredArgsConstructor
public class PaycoController {

    private final PaycoServiceImpl paycoService;
    private final UserServiceImpl userService;
    private final AuthServiceImpl authService;

    @Value("${payco.client-id}")
    private String clientId;

    @Value("${payco.client-secret}")
    private String clientSecret;

    @Value("${payco.redirect-uri}")
    private String redirectUri;

    @Value("${provider.payco.authorization-uri}")
    private String authorizationUri;

    @GetMapping("auth/login/payco")
    public RedirectView requestPaycoAuth() throws UnsupportedEncodingException {

        String authorizationUrl = authorizationUri + "?" +
                "response_type=code" +
                "&client_id=" + clientId +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8") +
                "&serviceProviderCode=FRIENDS" +
                "&userLocale=ko_KR";

        return new RedirectView(authorizationUrl);
    }

    @GetMapping("/callback")
    public String handleAuthorizationCallback(@RequestParam("code") String authorizationCode,
                                              HttpServletRequest request, HttpServletResponse response) {
        CreateAccessTokenResponse tokenResponse = paycoService.getAccessToken("authorization_code", clientId, clientSecret, authorizationCode);
        String paycoAccessToken = tokenResponse.accessToken();

        Member paycoInfo = paycoService.getPaycoInfo(clientId, paycoAccessToken);
        request.getSession().setAttribute("paycoInfo", paycoInfo);

        if (userService.isEmailDuplicate(paycoInfo.id())) {
           authService.loginUser(
                   LoginUserRequest.builder().email(paycoInfo.id()).password(paycoInfo.id()).build()
           );

           return "redirect:/";
        }

        return "redirect:/additional-info";
    }

    @GetMapping("/additional-info")
    public String showAdditionalInfoForm(HttpServletRequest request, Model model) {
        Member paycoInfo = (Member) request.getSession().getAttribute("paycoInfo");

        model.addAttribute("paycoInfo", paycoInfo);
        return "register-payco";
    }

    @PostMapping("/additional-info")
    public String processAdditionalInfo(@ModelAttribute CreateUserRequest form, HttpServletRequest request) {

        Member paycoInfo = (Member) request.getSession().getAttribute("paycoInfo");

        if (paycoInfo == null) {
            return "redirect:/error";
        }

        userService.signUp(paycoService.fillMissingInfo(paycoInfo, form));
        authService.loginUser(LoginUserRequest.builder().email(paycoInfo.id()).password(paycoInfo.id()).build());

        request.getSession().removeAttribute("paycoInfo");

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        deleteCookie(response, "AccessToken");
        deleteCookie(response, "RefreshToken");

        request.getSession().invalidate();

        return "redirect:/";
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}