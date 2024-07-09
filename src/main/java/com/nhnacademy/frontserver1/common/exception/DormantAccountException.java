package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class DormantAccountException extends ApplicationException{

    public DormantAccountException(
        ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
