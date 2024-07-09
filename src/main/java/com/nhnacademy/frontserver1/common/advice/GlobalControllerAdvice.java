package com.nhnacademy.frontserver1.common.advice;

import com.nhnacademy.frontserver1.common.exception.*;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(FeignClientException.class)
    public ResponseEntity<ErrorStatus> handleFeignClientException(FeignClientException e, Model model) {
        ErrorStatus errorStatus = e.getErrorStatus();

        log.error("error :", e);

        return new ResponseEntity<>(errorStatus, errorStatus.toHttpStatus());
    }

    @ExceptionHandler({RefreshTokenFailedException.class, TokenCookieMissingException.class})
    public ModelAndView handleRefreshTokenFailedException() {

        RedirectView redirectView = new RedirectView("/auth/error");
        redirectView.addStaticAttribute("cause", "알 수 없는 이유로 인해 인증 정보를 찾을 수 없습니다.");
        return new ModelAndView(redirectView);
    }

    @ExceptionHandler(OrderWaitingException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderWaitingException e) {
        log.warn("OrderWaitingException 발생: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }

    @ExceptionHandler(DormantAccountException.class)
    public String handleDormantAccountException(DormantAccountException e) {
        log.info("유저가 휴면 페이지로 리다이렉트됩니다.");
        
        return "redirect:/dormant";
    }
}
