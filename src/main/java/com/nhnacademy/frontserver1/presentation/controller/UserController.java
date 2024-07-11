package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.common.utils.CookieUtils;
import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.presentation.dto.request.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdatePasswordRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import com.nhnacademy.frontserver1.presentation.dto.request.user.FindPasswordRequest;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 페이지
    @GetMapping("/sign-up")
    public String signUp(HttpServletRequest request) {
        CookieUtils.validateAccessToken(request);

        return "register";
    }

    // 회원 가입
    @PostMapping("/sign-up")
    public String signUp(@RequestParam String userName, // todo : @ModelAttribute 변경 예정, 중복 이메일 예외 처리 예정
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
                .providerName("LOCAL")
                .build();

        UserResponse userResponse = userService.signUp(userRequest);

        model.addAttribute("userResponse", userResponse);

        return "redirect:/auth/login";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/mypage/info")
    public String userInfo(Model model) {

        UserResponse user = userService.findByUser();

        model.addAttribute("user", user);

        return "mypage/mypage-info";
    }

    // 회원 정보 수정
    @PutMapping("/mypage/info")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserRequest userRequest,
                                           Model model) {

        UpdateUserResponse user = userService.updateUser(userRequest);

        model.addAttribute("user", user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/mypage/delete")
    public String userDelete(Model model) {

        return "mypage/mypage-delete";
    }

    // 회원 탈퇴
    @DeleteMapping("/mypage/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest userRequest) {
        userService.deleteUser(userRequest);

        return ResponseEntity.ok().build();
    }

    // 회원 등급 페이지
    @GetMapping("/mypage/grades")
    public String getUserGrades(Model model) {

        // todo : 회원 등급 가져오는 로직

        UserGradeResponse userGrade = userService.getUserGrade();

        model.addAttribute("userGrade", userGrade);

        return "mypage/mypage-grade";
    }

    // 회원 현재 포인트와 포인트 이력 조회
    @GetMapping("/mypage/point-logs")
    public String getUserPoints(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                Model model) {

        Page<PointLogResponse> pointLogs = userService.getPointLogs(PageRequest.of(page, size));

        model.addAttribute("currentPoint", pointLogs.getContent().isEmpty() ? BigDecimal.ZERO : pointLogs.getContent().getFirst().pointCurrent());
        model.addAttribute("pointLogs", pointLogs);

        return "mypage/mypage-pointLogs";
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.isEmailDuplicate(email));
    }



    // 배송지 목록 조회
    @GetMapping("/mypage/addresses")
    public String getUserAddresses(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {

        Page<UserAddressResponse> userAddresses = userService.getAllUserAddresses(PageRequest.of(page, size));
        Optional<UserAddressResponse> defaultAddress = userAddresses.stream()
                .filter(UserAddressResponse::addressBased)
                .findFirst();


        model.addAttribute("addresses", userAddresses);
        model.addAttribute("defaultAddress", defaultAddress.orElse(null));


        return "mypage/mypage-address";
    }

    // 주소 기본 배송지 지정
    @PatchMapping("/mypage/addresses/{userAddressId}")
    public ResponseEntity<Void> updateAddressBased(@PathVariable Long userAddressId,
                                                   @RequestBody UpdateAddressBasedRequest request) {

        userService.updateAddressBased(userAddressId, request);

        return ResponseEntity.ok().build();
    }

    // 배송지 추가
    @PostMapping("/mypage/addresses")
    public ResponseEntity<CreateUserAddressResponse> createUserAddresses(@ModelAttribute CreateUserAddressRequest request) {
        return ResponseEntity.ok(userService.createUserAddresses(request));
    }

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/users/find-email")
    public String showFindEmailForm() {
        return "findMail/find-mail";
    }

    @PostMapping("/users/find-email")
    public String findEmail(@RequestParam String name, @RequestParam String phone, Pageable pageable, Model model) {
        try{
            List<FindUserResponse> emails = userService.findAllUserEmailByUserNameByUserPhone(name, phone, pageable);


            model.addAttribute("name", name);
            model.addAttribute("phone", phone);
            model.addAttribute("pageable", pageable);  // 추가된 부분
            //model.addAttribute("pageable" pageable);

            if (emails.isEmpty()) {
                return "findMail/find-mail-fail";
            } else {
                model.addAttribute("emails", emails);
                return "findMail/find-mail-success";
            }

        } catch (FeignClientException ex) {
            // 사용자 친화적인 에러 메시지와 함께 실패 페이지로 이동
            model.addAttribute("errorMessage", "회원이 존재하지 않습니다.");
            return "findMail/find-mail-fail";
        } catch (Exception ex) {
            // 기타 예외 처리
            model.addAttribute("errorMessage", "예상치 못한 오류가 발생했습니다.");
            return "error/500";
        }

    }

    @GetMapping("/mypage/coupons")
    public String getActiveUserCoupons(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "15") int size,
                                       Model model) {

        Page<CouponBoxResponse> activeCoupons = userService.getStateCouponBox("ACTIVE", PageRequest.of(page, size));
        Page<CouponBoxResponse> usedCoupons = userService.getStateCouponBox("USED", PageRequest.of(page, size));
        Page<CouponBoxResponse> expiredCoupons = userService.getStateCouponBox("EXPIRED", PageRequest.of(page, size));

        model.addAttribute("activeCoupons", activeCoupons);
        model.addAttribute("usedCoupons", usedCoupons);
        model.addAttribute("expiredCoupons", expiredCoupons);

        return "mypage/mypage-coupon";
    }

    // 페이코 회원 가입 페이지
    @GetMapping("/sign-up/payco")
    public String signUpPayco() {
        return "register-payco";
    }

    // 페이코 회원 가입
    @PostMapping("/sign-up/payco")
    public String signUpPayco(@RequestParam String userName,
                         @RequestParam LocalDate userBirth,
                         @RequestParam String userEmail,
                         @RequestParam String userPhone,
                         Model model) {

        CreateUserRequest userRequest = CreateUserRequest.builder()
                .userName(userName)
                .userBirth(userBirth)
                .userEmail(userEmail)
                .userPhone(userPhone)
                .providerName("PAYCO")
                .build();

        UserResponse userResponse = userService.signUp(userRequest);

        model.addAttribute("userResponse", userResponse);

        return "redirect:/auth/login";
    }

    @GetMapping("/users/find/password")
    public String showFindPasswordForm(){
        return "findPassword/find-password";
    }

    @PostMapping("/users/find/password")
    public String findPassword(@RequestParam String email, @RequestParam String name, Model model) {
        FindPasswordRequest request = new FindPasswordRequest(email, name);
        boolean isUserFound = userService.findUserPasswordByEmailByName(request);

        if (isUserFound) {
            userService.sendEmail(email);
            return "findPassword/send-mail-success";
        } else {
            return "findPassword/send-mail-fail";
        }
    }



    @GetMapping("/reset-password/{email}")
    public String showResetPasswordForm(@PathVariable("email") String email, Model model){
        model.addAttribute("email", email);
        return "setNewPassword/set-new-password";

    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        UpdatePasswordRequest request = new UpdatePasswordRequest(newPassword, confirmPassword);
        boolean isPasswordReset = userService.setUserPasswordByEmail(email, request);

        if (isPasswordReset) {
            return "setNewPassword/set-new-password-success";
        } else {
            return "setNewPassword/set-new-password-fail";
        }
    }
}