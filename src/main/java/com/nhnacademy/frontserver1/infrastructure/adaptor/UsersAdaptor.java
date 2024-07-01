package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usersAdaptor", url = "http://localhost:8085")
public interface UsersAdaptor {

    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable("userId") Long userId);
}