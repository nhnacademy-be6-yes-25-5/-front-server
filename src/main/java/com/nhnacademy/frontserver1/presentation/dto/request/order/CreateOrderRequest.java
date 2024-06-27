package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateOrderRequest(Long userId,
                                 String orderId,
                                 BigDecimal orderTotalAmount,
                                 TakeoutType takeoutType,
                                 String addressRaw,
                                 String addressDetail,
                                 String zipcode,
                                 String reference,
                                 LocalDate deliveryDate,
                                 String orderName,
                                 String orderEmail,
                                 String orderPhoneNumber,
                                 String receiveName,
                                 String receiveEmail,
                                 String receivePhoneNumber,
                                 Long couponId,
                                 BigDecimal shippingFee,
                                 BigDecimal points,
                                 BigDecimal takeoutPrice,
                                 BigDecimal discountPrice,
                                 List<Long> productIds,
                                 List<Integer> quantities,
                                 List<BigDecimal> prices,
                                 List<Long> cartBookIds,
                                 String role) {
}
