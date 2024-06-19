package com.nhnacademy.frontserver1.application.service.dto.request;

import com.nhnacademy.frontserver1.domain.TakeoutType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record CreatePreOrderRequest(String orderId,
                                    Long userId,
                                    List<Long> productIds,
                                    List<Integer> quantities,
                                    List<BigDecimal> prices,
                                    BigDecimal orderTotalAmount,
                                    BigDecimal discountPrice,
                                    BigDecimal shippingFee,
                                    BigDecimal takeoutPrice,
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
                                    BigDecimal points) {

}
