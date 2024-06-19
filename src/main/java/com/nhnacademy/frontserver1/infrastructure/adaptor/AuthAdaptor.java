package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.request.user.LoginUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.LoginUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVER", url = "http://localhost:8050")
public interface AuthAdaptor {

    @PostMapping("/login")
    ResponseEntity<Void> findLoginUserByEmail(@RequestBody LoginUserRequest loginUserRequest);

}