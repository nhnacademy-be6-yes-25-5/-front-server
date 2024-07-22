package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class LoginException extends ApplicationException{
    public LoginException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
