package com.nhnacademy.frontserver1.common.handler;

import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.common.exception.TokenCookieMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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

}
