package com.nhnacademy.frontserver1.common.advice;

import com.nhnacademy.frontserver1.application.service.UserService;
import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.common.exception.OrderWaitingException;
import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerAdvice {

    private final UserService userService;

    @ExceptionHandler(FeignClientException.class)
    public String handleFeignClientException(FeignClientException e, Model model) {

        log.error("error :", e);

        return "404";
    }

    @ExceptionHandler(TokenCookieMissingException.class)
    public ModelAndView handleTokenExpiredException(TokenCookieMissingException e) {

        log.error("error :", e);

        return new ModelAndView(new RedirectView("/auth/login"));
    }

    @ExceptionHandler(OrderWaitingException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderWaitingException e) {
        log.warn("OrderWaitingException 발생: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    @ModelAttribute("userInfo")
    public ReadUserInfoResponse addUserInfo() {
        return userService.getUserPointsAndGrade();
    }
}
