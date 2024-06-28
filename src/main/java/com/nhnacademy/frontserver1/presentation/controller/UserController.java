package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UpdateUserResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserGradeResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 가입 페이지
    @GetMapping("/sign-up")
    public String signUp() {
        return "/register";
    }

    // 회원 가입
    @PostMapping("/sign-up")
    public String signUp(@RequestParam String userName,
                         @RequestParam LocalDate userBirth,
                         @RequestParam String userEmail,
                         @RequestParam String userPhone,
                         @RequestParam String userPassword,
                         @RequestParam String userConfirmPassword,
                         Model model) {

        CreateUserRequest userRequest = CreateUserRequest.builder()
                .userName(userName)
                .userBirth(userBirth)
                .userEmail(userEmail)
                .userPhone(userPhone)
                .userPassword(userPassword)
                .userConfirmPassword(userConfirmPassword)
                .build();

        UserResponse userResponse = userService.signUp(userRequest);

        model.addAttribute("userResponse", userResponse);

        return "redirect:/auth/login";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/info")
    public String userInfo(Model model) {

        UserResponse user = userService.findByUserId();

        model.addAttribute("user", user);

        return "mypage/mypage-info";
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequest userRequest,
                             Model model) {

        UpdateUserResponse user = userService.updateUser(userRequest);

        model.addAttribute("user", user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    public String userDelete(Model model) {

        return "mypage/mypage-delete";
    }

    // 회원 탈퇴
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest userRequest) {
        userService.deleteUser(userRequest);

        return ResponseEntity.ok().build();
    }

    // 회원 등급 페이지
    @GetMapping("/grades")
    public String getUserGrades(Model model) {

        // todo : 회원 등급 가져오는 로직

        UserGradeResponse userGrade = userService.getUserGrade();

        model.addAttribute("userGrade", userGrade);

        return "mypage/mypage-grade";
    }
}
