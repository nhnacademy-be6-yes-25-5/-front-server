package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class LikesNotLoginException extends ApplicationException {
    public LikesNotLoginException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
