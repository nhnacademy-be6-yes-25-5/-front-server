package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.auth.CreatePaycoInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paycoInfoAdaptor", url = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json")
public interface PaycoInfoAdaptor {

    @PostMapping
    ResponseEntity<CreatePaycoInfoResponse> getPaycoInfo(@RequestHeader HttpHeaders headers);
}