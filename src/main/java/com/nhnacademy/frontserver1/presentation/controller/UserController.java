package com.nhnacademy.frontserver1.presentation.controller;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.presentation.dto.request.user.CreateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.DeleteUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.request.user.UpdateUserRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.point.PointLogResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.*;
import com.nhnacademy.frontserver1.presentation.dto.response.address.UserAddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 가입 페이지
    @GetMapping("/sign-up")
    public String signUp() {
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
                .build();

        UserResponse userResponse = userService.signUp(userRequest);

        model.addAttribute("userResponse", userResponse);

        return "redirect:/auth/login";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/info")
    public String userInfo(Model model) {

        UserResponse user = userService.findByUser();

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

    // 회원 현재 포인트와 포인트 이력 조회
    @GetMapping("/points/logs")
    public String getUserPoints(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size,
                                Model model) {

        Page<PointLogResponse> pointLogs = userService.getPointLogs(PageRequest.of(page, size));

        model.addAttribute("currentPoint", pointLogs.getContent().isEmpty() ? BigDecimal.ZERO : pointLogs.getContent().getFirst().pointCurrent());
        model.addAttribute("pointLogs", pointLogs);

        return "mypage/mypage-pointLogs";
    }

    @GetMapping("/{userId}/addresses")
    public String getUserAddresses(@PathVariable Long userId, Model model) {
        Pageable pageable = PageRequest.of(0, 10);
        UsersResponse user = userService.getUserById(userId);
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

  //  private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/find-email")
    public String showFindEmailForm() {
        return "findMail/find-mail";
    }

    @PostMapping("/find-email")
    public String findEmail(@RequestParam String name, @RequestParam String phone, Pageable pageable, Model model) {
        try{

            // 입력 값 로그 출력
//            logger.info("프론트 Received name: " + name);
//            logger.info("프론트 Received phone: " + phone);
//            logger.info("프론트 Pageable: " + pageable);
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

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(Exception ex) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("파인드메일페일이안되는이유 message", ex.getMessage());
//        modelAndView.setViewName("error/500");
//        return modelAndView;
//    }
    //
//    @GetMapping("/login")
//    public String showLoginForm(){
//        return "login";
//    }
}
