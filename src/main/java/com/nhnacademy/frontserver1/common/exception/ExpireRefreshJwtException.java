package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class ExpireRefreshJwtException extends ApplicationException {

    public ExpireRefreshJwtException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
