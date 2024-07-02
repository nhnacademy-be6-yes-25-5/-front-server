package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPurePriceResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "userAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface UserAdaptor {

    @GetMapping("/orders/info")
    ReadOrderUserInfoResponse getUserInfo();

    @PostMapping("/coupons/claim")
    void claimCoupon(@RequestParam Long couponId);

    @GetMapping("/pure-price")
    ReadPurePriceResponse getPurePrice();

    @GetMapping("/grade")
    ReadUserInfoResponse getUserPointsAndGrade();
}
