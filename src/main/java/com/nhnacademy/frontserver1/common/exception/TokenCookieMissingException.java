package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class TokenCookieMissingException extends ApplicationException {



    public TokenCookieMissingException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}