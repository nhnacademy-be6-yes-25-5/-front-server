package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadPurePriceResponse;
import com.nhnacademy.frontserver1.presentation.dto.response.user.ReadUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface UserAdaptor {

    @GetMapping("/orders/info")
    ReadOrderUserInfoResponse getUserInfo();

    @GetMapping("/pure-price")
    ReadPurePriceResponse getPurePrice();

    @GetMapping("/grade")
    ReadUserInfoResponse getUserPointsAndGrade();
}
