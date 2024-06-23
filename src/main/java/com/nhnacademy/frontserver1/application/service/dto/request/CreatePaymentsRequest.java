package com.nhnacademy.frontserver1.application.service.dto.request;

import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import java.util.List;
import lombok.Builder;

@Builder
public record CreatePaymentsRequest(String paymentKey,
                                    String orderId,
                                    Integer amount,
                                    List<Long> bookIds,
                                    List<Integer> quantities) {

    public static CreatePaymentsRequest of(CreatePaymentRequest request, List<Long> bookIds,
        List<Integer> quantities) {
        return CreatePaymentsRequest.builder()
            .paymentKey(request.paymentKey())
            .orderId(request.orderId())
            .amount(request.amount())
            .bookIds(bookIds)
            .quantities(quantities)
            .build();
    }
}
