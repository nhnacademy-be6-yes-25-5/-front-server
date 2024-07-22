package com.nhnacademy.frontserver1.presentation.dto.request.order;

import com.nhnacademy.frontserver1.domain.PaymentProvider;
import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateOrderRequest(String orderId,
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
                                 List<Long> couponIds,
                                 BigDecimal shippingFee,
                                 BigDecimal points,
                                 BigDecimal takeoutPrice,
                                 BigDecimal discountPrice,
                                 List<Long> productIds,
                                 List<Integer> quantities,
                                 List<BigDecimal> prices,
                                 String role,
                                 String cartId,
                                 PaymentProvider paymentProvider) {
}
