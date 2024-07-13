package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class UnauthorizedAccessException extends ApplicationException {

    public UnauthorizedAccessException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
