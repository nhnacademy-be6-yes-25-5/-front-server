package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.domain.CancelStatus;

public record CancelOrderRequest(CancelStatus status) {

}
