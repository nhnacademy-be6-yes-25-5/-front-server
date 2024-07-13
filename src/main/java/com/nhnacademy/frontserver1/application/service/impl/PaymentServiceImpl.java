package com.nhnacademy.frontserver1.application.service.impl;

import com.nhnacademy.frontserver1.application.service.PaymentService;
import com.nhnacademy.frontserver1.application.service.dto.request.CreatePaymentsRequest;
import com.nhnacademy.frontserver1.common.exception.ApplicationException;
import com.nhnacademy.frontserver1.common.exception.payload.ErrorStatus;
import com.nhnacademy.frontserver1.infrastructure.adaptor.PaymentAdaptor;
import com.nhnacademy.frontserver1.presentation.dto.request.payment.CreatePaymentRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.CreatePaymentResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPaymentOrderResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentAdaptor paymentAdaptor;

    @Override
    public CreatePaymentResponse createPayment(CreatePaymentRequest request,
        List<Long> bookIds,
        List<Integer> quantities) {

        CreatePaymentsRequest paymentsRequest = CreatePaymentsRequest.of(request, bookIds, quantities);
        CreatePaymentResponse response = paymentAdaptor.createPayment(paymentsRequest).getBody();

        if (response.status() != 200) {
            log.error("결제에 실패했습니다.");
            throw new ApplicationException(
                ErrorStatus.toErrorStatus("결제에 실패했습니다.", 500, LocalDateTime.now()));
        }

        return response;
    }

    @Override
    public List<ReadPaymentOrderResponse> findAllOrderByOrderId(String orderId) {
        return paymentAdaptor.findAllByOrderId(orderId).getBody();
    }

    @Override
    public CreatePaymentResponse createPaymentByZeroAmount(CreatePaymentRequest request,
        Integer totalAmount, List<Long> bookIds, List<Integer> quantities) {

        CreatePaymentsRequest paymentsRequest = CreatePaymentsRequest.of(request, bookIds, quantities);
        return paymentAdaptor.createPaymentByZeroAmount(paymentsRequest).getBody();
    }
}
