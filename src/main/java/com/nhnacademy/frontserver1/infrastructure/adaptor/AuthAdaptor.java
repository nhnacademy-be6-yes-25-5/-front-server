package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "authAdaptor", url = "${eureka.gateway}/auth")
public interface AuthAdaptor {

    @PostMapping("/login")
    ResponseEntity<String> findLoginUserByEmail(@RequestBody LoginUserRequest loginUserRequest);

    @GetMapping("/test")
    ResponseEntity<String> tokenTest();

}