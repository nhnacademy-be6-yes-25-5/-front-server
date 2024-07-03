package com.nhnacademy.frontserver1.common.handler;

import com.nhnacademy.frontserver1.common.exception.ExpireRefreshJwtException;
import com.nhnacademy.frontserver1.common.exception.RefreshTokenFailedException;
import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import com.nhnacademy.frontserver1.common.exception.OrderWaitingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignClientException.class)
    public String handleFeignClientException(FeignClientException e, Model model) {

        log.error("error :", e);

        return "404";
    }

    @ExceptionHandler(ExpireRefreshJwtException.class)
    public ModelAndView handleExpireRefreshJwtException(ExpireRefreshJwtException e) {

        log.error("ExpireRefreshJwtException 발생 :", e);

        return new ModelAndView(new RedirectView("/auth/login"));
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
}
