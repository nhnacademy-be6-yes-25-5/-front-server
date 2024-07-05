package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.auth.DeleteAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paycoLogoutAdaptor", url = "${provider.payco.logout-uri}")
public interface PaycoLogoutAdaptor {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    DeleteAccessTokenResponse deleteAccessToken(@RequestParam("token") String accessToken,
                                                @RequestParam("client_id") String clientId,
                                                @RequestParam("client_secret") String clientSecret);

}
