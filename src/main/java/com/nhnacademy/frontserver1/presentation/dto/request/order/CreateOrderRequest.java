package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.application.service.dto.request.CreatePreOrderRequest;
import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest(String orderId,
                                 BigDecimal orderTotalAmount,
                                 TakeoutType takeoutType,
                                 String addressRaw,
                                 String addressDetail,
                                 String zipcode,
                                 String reference,
                                 LocalDateTime deliveryDate,
                                 String orderName,
                                 String orderEmail,
                                 String orderPhoneNumber,
                                 String receiveName,
                                 String receiveEmail,
                                 String receivePhoneNumber,
                                 Long couponId,
                                 BigDecimal points,
                                 List<Long> productIds,
                                 List<Integer> quantities,
                                 List<BigDecimal> prices) {

    public CreatePreOrderRequest toPreOrderRequest(Long userId) {
        return CreatePreOrderRequest.builder()
            .orderId(orderId)
            .userId(userId)
            .productIds(productIds)
            .prices(prices)
            .quantities(quantities)
            .addressDetail(addressDetail)
            .addressRaw(addressRaw)
            .zipcode(zipcode)
            .reference(reference)
            .deliveryDate(deliveryDate)
            .orderTotalAmount(orderTotalAmount)
            .takeoutType(takeoutType)
            .orderName(orderName)
            .orderEmail(orderEmail)
            .orderPhoneNumber(orderPhoneNumber)
            .receiveName(receiveName)
            .receiveEmail(receiveEmail)
            .receivePhoneNumber(receivePhoneNumber)
            .couponId(couponId)
            .points(points)
            .build();
    }
}
