package com.nhnacademy.frontserver1.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import com.nhnacademy.frontserver1.application.service.impl.PaycoServiceImpl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;

@Controller
@RequiredArgsConstructor
public class PaycoController {

    private final PaycoServiceImpl paycoService;

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
                                              Model model) {
        CreateAccessTokenResponse response = paycoService.getAccessToken("authorization_code", clientId, clientSecret, authorizationCode);
        String accessToken = response.accessTokenSecret();
        CreatePaycoInfoResponse paycoInfo = paycoService.getPaycoInfo(clientId, accessToken);


        return "index";
    }

}
