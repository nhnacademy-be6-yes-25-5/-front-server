package com.nhnacademy.frontserver1.presentation.dto.response;

public record ApiResponse<T> (T body, int status) {
}
