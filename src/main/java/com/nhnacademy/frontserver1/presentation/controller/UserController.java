package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}/addresses")
    public String getUserAddresses(@PathVariable Long userId, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        UserResponse user = userService.getUserById(userId);
        Page<UserAddressResponse> addressPage = userService.getUserAddresses(userId, pageable);

        model.addAttribute("userName", user.userName());
        model.addAttribute("userGrade", user.userGrade());
        model.addAttribute("userPoints", user.userPoints());
        model.addAttribute("defaultAddress", user.defaultAddress());
        model.addAttribute("addresses", addressPage.getContent());
        model.addAttribute("currentPage", addressPage.getNumber());
        model.addAttribute("totalPages", addressPage.getTotalPages());
        model.addAttribute("pageSize", addressPage.getSize());

        return "mypage/mypage-address";
    }
}