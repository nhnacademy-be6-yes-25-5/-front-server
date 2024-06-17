package com.nhnacademy.frontserver1.presentation.dto.request.order.request;

import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest(Long userId,
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
                                 String receivePhoneNumber) {
}
