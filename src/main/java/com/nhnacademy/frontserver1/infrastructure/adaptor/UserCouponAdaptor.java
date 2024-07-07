package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.application.service.dto.request.ReadMaximumDiscountCouponRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMaximumDiscountCouponResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadUserCouponResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userCouponAdaptor", url = "${eureka.gateway}/users")
public interface UserCouponAdaptor {

    @GetMapping("/user-coupons")
    List<ReadUserCouponResponse> getAllUserCoupons();

    @GetMapping("/user-coupons/max")
    ReadMaximumDiscountCouponResponse getMaxDiscountCouponByTotalAmount(
        ReadMaximumDiscountCouponRequest request);
}
