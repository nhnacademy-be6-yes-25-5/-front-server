package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class AccessDeniedException extends ApplicationException {

    public AccessDeniedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
