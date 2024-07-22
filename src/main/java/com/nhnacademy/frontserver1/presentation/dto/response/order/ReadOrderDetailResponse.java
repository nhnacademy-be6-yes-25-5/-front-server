package com.nhnacademy.frontserver1.presentation.dto.response.order;

import com.nhnacademy.frontserver1.domain.CancelStatus;
import com.nhnacademy.frontserver1.domain.PaymentProvider;
import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReadOrderDetailResponse(String orderId,
                                      Long customerId,
                                      BigDecimal orderTotalAmount,
                                      OrderStatusType orderStatusType,
                                      String addressRaw,
                                      String addressDetail,
                                      String zipCode,
                                      LocalDateTime orderCreatedAt,
                                      LocalDateTime deliveryStartedAt,
                                      LocalDate orderDeliveryAt,
                                      String orderUserName,
                                      String orderUserEmail,
                                      Integer bookTotalPrice,
                                      Integer shippingPrice,
                                      String orderUserPhoneNumber,
                                      String receiveUserName,
                                      String receiveUserEmail,
                                      String receiveUserPhoneNumber,
                                      String userRole,
                                      String reference,
                                      Long couponId,
                                      BigDecimal points,
                                      List<String> bookNames,
                                      List<Integer> quantities,
                                      List<BigDecimal> bookPrices,
                                      CancelStatus cancelStatus,
                                      PaymentProvider paymentProvider) {

}
