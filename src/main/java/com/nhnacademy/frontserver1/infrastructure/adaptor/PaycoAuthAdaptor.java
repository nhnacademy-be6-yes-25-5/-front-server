package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreateAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paycoAuthAdaptor", url = "${provider.payco.token-uri}")
public interface PaycoAuthAdaptor {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CreateAccessTokenResponse getAccessToken(@RequestParam("grant_type") String grantType,
                                             @RequestParam("client_id") String clientId,
                                             @RequestParam("client_secret") String clientSecret,
                                             @RequestParam("code") String authorizationCode);

    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CreateAccessTokenResponse refreshAccessToken(@RequestParam("grant_type") String grantType,
                                                 @RequestParam("client_id") String clientId,
                                                 @RequestParam("client_secret") String clientSecret,
                                                 @RequestParam("refresh_token") String refreshToken);

}
