package com.nhnacademy.frontserver1.presentation.dto.request.order.request;

import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CreateOrderRequest(BigDecimal orderTotalAmount,
                                 TakeoutType takeoutType,
                                 String addressRaw,
                                 String addressDetail,
                                 String zipcode,
                                 LocalDateTime deliveryDate) {

}
