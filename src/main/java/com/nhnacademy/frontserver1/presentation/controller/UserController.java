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
import org.springframework.data.web.PageableDefault;
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

    // 회원 탈퇴 페이지
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
    public String getUserPoints(@PageableDefault(size = 15) Pageable pageable,
                                Model model) {

        Page<PointLogResponse> pointLogs = userService.getPointLogs(pageable);

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
    public String getUserAddresses(@PageableDefault Pageable pageable,
                                   Model model) {

        Page<UserAddressResponse> userAddresses = userService.getAllUserAddresses(pageable);
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

    // 배송지 수정
    @PutMapping("/mypage/addresses/{userAddressId}")
    public ResponseEntity<UpdateUserAddressResponse> updateUserAddress(@PathVariable Long userAddressId,
                                                                       @RequestBody UpdateUserAddressRequest request) {
        return ResponseEntity.ok(userService.updateUserAddress(userAddressId, request));
    }

    // 배송지 단건 조회
    @GetMapping("/mypage/addresses/{userAddressId}")
    public ResponseEntity<UserAddressResponse> findUserAddressById(@PathVariable Long userAddressId) {
        return ResponseEntity.ok(userService.findUserAddressById(userAddressId));
    }

    @DeleteMapping("/mypage/addresses/{userAddressId}")
    public ResponseEntity<Void> deleteUserAddressById(@PathVariable Long userAddressId) {

        userService.deleteUserAddress(userAddressId);

        return ResponseEntity.ok().build();
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

    @PutMapping("/reset-password/[{email}")
    public String resetPassword(@RequestBody String email, @RequestBody String newPassword, @RequestBody String confirmPassword, Model model) {
       // UpdatePasswordRequest request = new UpdatePasswordRequest(newPassword, confirmPassword);
        //boolean isPasswordReset = userService.setUserPasswordByEmail(email, request);

        UpdatePasswordRequest request = UpdatePasswordRequest.builder()
                .userPassword(newPassword)
                .confirmPassword(confirmPassword)
                .build();

       // boolean isPasswordReset = userService.UpdateUserPasswordByEmail(email, request);


        if (newPassword.equals(confirmPassword)) {
            return "setNewPassword/set-new-password-success";
        } else {
            return "setNewPassword/set-new-password-fail";
        }
//@Builder
//public record UpdatePasswordRequest(String userPassword, String confirmPassword) {}
//        if (isPasswordReset) {
//            return "setNewPassword/set-new-password-success";
//        } else {
//            return "setNewPassword/set-new-password-fail";
//        }
    }

//    @PostMapping("/reset-password/{email}")
//        public String resetPassword(@PathVariable String email, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
//        if (!newPassword.equals(confirmPassword)) {
//            model.addAttribute("error", "Passwords do not match");
//            return "setNewPassword/set-new-password";
//        }
//        //UpdateUserRequest(String userName, String userPhone, LocalDate userBirth, String userPassword,
//        //                                String newUserPassword, String newUserConfirmPassword)
//        UpdateUserRequest request = UpdateUserRequest.builder()
//                .userName(null)  // 유저 이름을 업데이트하지 않으므로 null
//                .userPhone(null)  // 유저 전화번호를 업데이트하지 않으므로 null
//                .userBirth(null)  // 유저 생일을 업데이트하지 않으므로 null
//                .userPassword(newPassword)  // 현재 비밀번호가 필요하지 않으므로 null
//                .newUserConfirmPassword(confirmPassword)
//                .build();
//
//
////            boolean isPasswordReset;
////            try {
////                UpdatePasswordResponse response = userService.updateUser(request);
////
////
////                isPasswordReset = response != null;
////            } catch (Exception e) {
////                isPasswordReset = false;
////            }
////
////            if (isPasswordReset) {
////                return "setNewPassword/set-new-password-success";
////            } else {
////                model.addAttribute("error", "Password update failed");
////                return "setNewPassword/set-new-password-fail";
////            }
////            return null; }
//
//
//    }
        //@PutMapping("/info")
    //    UpdateUserResponse updateUser(@RequestBody UpdateUserRequest userRequest);
}