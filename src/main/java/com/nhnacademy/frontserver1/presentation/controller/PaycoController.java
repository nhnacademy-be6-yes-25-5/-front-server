package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.impl.UserServiceImpl;
import com.nhnacademy.frontserver1.application.service.impl.AuthServiceImpl;
import com.nhnacademy.frontserver1.presentation.dto.response.user.AuthResponse;
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
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse.Member;

@Controller
@RequiredArgsConstructor
public class PaycoController {

    private final PaycoServiceImpl paycoService;
    private final UserServiceImpl userServiceImpl;
    private final AuthServiceImpl authServiceImpl;

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
                                              HttpServletRequest request,
                                              HttpServletResponse response) {
        CreateAccessTokenResponse tokenResponse = paycoService.getAccessToken("authorization_code", clientId, clientSecret, authorizationCode);
        String accessToken = tokenResponse.accessToken();

        Member paycoInfo = paycoService.getPaycoInfo(clientId, accessToken);
        request.getSession().setAttribute("paycoInfo", paycoInfo);

        AuthResponse authResponse = paycoService.processPaycoLogin(paycoInfo);

        // 토큰 쿠키 설정
        addTokenCookie(response, "AccessToken", authResponse.accessToken());
        addTokenCookie(response, "RefreshToken", authResponse.refreshToken());

        if (paycoService.isRequiredInfoMissing(paycoInfo)) {
            return "redirect:/additional-info";
        } else {
            return "index";
        }
    }

    private void addTokenCookie(HttpServletResponse response, String name, String token) {
        if (token != null) {
            Cookie cookie = new Cookie(name, token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    @GetMapping("/additional-info")
    public String showAdditionalInfoForm(HttpServletRequest request, Model model) {
        CreatePaycoInfoResponse paycoInfo = (CreatePaycoInfoResponse) request.getSession().getAttribute("paycoInfo");
        model.addAttribute("paycoInfo", paycoInfo);
        return "register-payco";
    }

    @PostMapping("/additional-info")
    public String processAdditionalInfo(@ModelAttribute CreateUserRequest form, HttpServletRequest request) {

        Member paycoInfo = (Member) request.getSession().getAttribute("paycoInfo");

        if (paycoInfo == null) {
            return "redirect:/error";
        }

        userServiceImpl.signUp(paycoService.fillMissingInfo(paycoInfo, form));

        request.getSession().removeAttribute("paycoInfo");

        return "redirect:/main";
    }
}