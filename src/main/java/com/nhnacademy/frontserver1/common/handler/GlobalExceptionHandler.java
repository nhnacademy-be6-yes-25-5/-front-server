package com.nhnacademy.frontserver1.common.handler;


import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(FeignClientException.class)
    public String handleFeignClientException(FeignClientException e, Model model) {
        log.error("error :", e);

        return "404";
    }
}
