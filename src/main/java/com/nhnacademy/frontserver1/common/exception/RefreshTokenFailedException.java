package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class RefreshTokenFailedException extends ApplicationException {

    public RefreshTokenFailedException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
