package com.nhnacademy.frontserver1.common.exception;

import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class OrderWaitingException extends ApplicationException {

    public OrderWaitingException(
        ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
