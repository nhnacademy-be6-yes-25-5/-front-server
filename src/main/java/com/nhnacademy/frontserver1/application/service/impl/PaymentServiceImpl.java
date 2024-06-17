package com.nhnacademy.frontserver1.application.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaymentAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.order.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentAdaptor paymentAdaptor;

    // todo. 요청을 dto로 받도록 수정
    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request) {

        try {
            String requestBody = new ObjectMapper().writeValueAsString(request);

            return paymentAdaptor.createPayment(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
