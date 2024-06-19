package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.application.service.dto.request.CreatePaymentsRequest;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaymentAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentAdaptor paymentAdaptor;

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request,
        List<Long> bookIds,
        List<Integer> quantities) {

        CreatePaymentsRequest paymentsRequest = CreatePaymentsRequest.of(request, bookIds, quantities);

        return paymentAdaptor.createPayment(paymentsRequest);
    }

}
