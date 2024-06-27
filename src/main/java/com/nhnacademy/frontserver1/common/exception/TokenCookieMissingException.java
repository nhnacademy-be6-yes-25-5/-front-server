package com.nhnacademy.frontserver1.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenCookieMissingException extends ResponseStatusException {

    public TokenCookieMissingException() {
        super(HttpStatus.UNAUTHORIZED, "Authorization 쿠키가 없습니다. 로그인이 필요합니다.");
    }

}