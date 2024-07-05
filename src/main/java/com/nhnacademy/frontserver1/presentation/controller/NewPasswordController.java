package com.nhnacademy.frontserver1.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NewPasswordController {

    @GetMapping("/set-new-password")
    public String newPassword(){
        return "/setNewPassword/set-new-password";
    }

}
