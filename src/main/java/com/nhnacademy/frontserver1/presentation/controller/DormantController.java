package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.AuthService;
import com.nhnacademy.frontserver1.presentation.dto.request.dormant.SubmitAuthNumberRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.dormant.CreateAuthNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dormant")
public class DormantController {

    private final AuthService authService;

    @GetMapping
    public String getDormantPage() {
        return "dormant/dormant";
    }

    @PostMapping("/auth")
    public ResponseEntity<Void> createAuthNumber(@RequestBody CreateAuthNumberRequest request) {
        authService.createAuthNumber(request);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> submitAuthNumber(@RequestBody SubmitAuthNumberRequest request) {

        return ResponseEntity.ok(authService.submitAuthNumber(request));
    }
}
