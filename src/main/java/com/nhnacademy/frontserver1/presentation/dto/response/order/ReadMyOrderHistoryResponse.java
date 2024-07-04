package com.nhnacademy.frontserver1.presentation.dto.response.order;

import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ReadMyOrderHistoryResponse(String orderId,
                                         Integer orderTotalAmount,
                                         LocalDateTime orderCreatedAt,
                                         LocalDateTime deliveryStartedAt,
                                         LocalDate orderDeliveryAt,
                                         OrderStatusType orderStatus,
                                         List<Long> bookIds,
                                         List<Integer> quantities,
                                         List<String> bookNames,
                                         BigDecimal purePrice) {

}
