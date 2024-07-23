package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.application.service.dto.request.ReadMaximumDiscountCouponRequest;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadMaximumDiscountCouponResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadUserCouponResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userCouponAdaptor", url = "${eureka.gateway}/users")
public interface UserCouponAdaptor {

    @GetMapping("/user-coupons")
    ResponseEntity<List<ReadUserCouponResponse>> getAllUserCoupons();

    @GetMapping("/user-coupons/max")
    ReadMaximumDiscountCouponResponse getMaxDiscountCouponByTotalAmount(
        @RequestParam Integer totalAmount);
}
