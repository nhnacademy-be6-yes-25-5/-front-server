package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class BookException extends ApplicationException{
    public BookException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
