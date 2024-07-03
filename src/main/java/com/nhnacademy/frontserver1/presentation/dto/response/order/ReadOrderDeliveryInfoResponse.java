package com.nhnacademy.frontserver1.presentation.dto.response.order;

import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ReadOrderDeliveryInfoResponse(String orderId, Long customerId, BigDecimal orderTotalAmount,
                                            LocalDateTime orderCreatedAt, OrderStatusType orderStatusType,
                                            String addressRaw, String addressDetail, String zipCode, String reference,
                                            String orderUserName, String orderUserEmail,
                                            String orderUserPhoneNumber, String receiveUserName,
                                            String receiveUserEmail, String receiveUserPhoneNumber,
                                            List<String> bookNames,
                                            List<String> bookAuthors,
                                            List<Integer> bookPrices,
                                            List<LocalDateTime> timestamp,
                                            List<String> deliveryStatuses) {

}
