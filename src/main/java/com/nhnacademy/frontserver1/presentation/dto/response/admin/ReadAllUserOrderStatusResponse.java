package com.nhnacademy.frontserver1.presentation.dto.response.admin;

import com.nhnacademy.frontserver1.presentation.dto.response.admin.enumtype.OrderStatusType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReadAllUserOrderStatusResponse(String orderId,
                                             Long customerId,
                                             List<String> bookNames,
                                             List<Long> bookIds,
                                             List<Integer> quantities,
                                             LocalDate orderCreatedAt,
                                             LocalDate orderDeliveryAt,
                                             BigDecimal amount,
                                             OrderStatusType orderStatusType) {

}
