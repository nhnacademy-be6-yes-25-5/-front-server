package com.nhnacademy.frontserver1.common.exception;


import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;

public class FeignClientException extends ApplicationException {

    public FeignClientException(ErrorStatus errorStatus) {
        super(errorStatus);
    }

}
