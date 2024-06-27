package com.nhnacademy.frontserver1.common.handler;


import com.nhnacademy.frontserver1.common.exception.FeignClientException;
import com.nhnacademy.frontserver1.common.exception.OrderWaitingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(OrderWaitingException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderWaitingException e) {
        log.warn("OrderWaitingException 발생: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(e.getMessage());
    }
}
