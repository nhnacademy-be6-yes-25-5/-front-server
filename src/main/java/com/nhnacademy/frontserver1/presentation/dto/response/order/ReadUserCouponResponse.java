package com.nhnacademy.frontserver1.presentation.dto.response.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record ReadUserCouponResponse(Long userCouponId,
                                     Date CouponExpiredAt,
                                     Long couponId,

                                     String couponName,
                                     BigDecimal couponMinAmount,
                                     BigDecimal couponDiscountAmount,
                                     BigDecimal couponDiscountRate,
                                     Long bookId,
                                     List<Long> categoryIds,
                                     Boolean applyCouponToAllBooks) {

}
