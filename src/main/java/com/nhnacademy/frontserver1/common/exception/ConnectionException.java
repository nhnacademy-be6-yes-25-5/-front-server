package com.nhnacademy.frontserver1.common.exception;


import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class ConnectionException extends ApplicationException {

    public ConnectionException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
