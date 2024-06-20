package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "GATEWAY-SERVER", url = "http://localhost:8085")
public interface AuthAdaptor {

    @PostMapping("/auth/login")
    ResponseEntity<Void> findLoginUserByEmail(@RequestBody LoginUserRequest loginUserRequest);

}