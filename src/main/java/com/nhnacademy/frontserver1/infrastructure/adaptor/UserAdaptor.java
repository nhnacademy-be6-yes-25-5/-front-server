package com.nhnacademy.frontserver1.infrastructure.adaptor;

import com.nhnacademy.frontserver1.common.config.FeignClientConfig;
import com.nhnacademy.frontserver1.presentation.dto.response.order.ReadOrderUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userAdaptor", url = "${eureka.gateway}/users", configuration = FeignClientConfig.class)
public interface UserAdaptor {

    @GetMapping("/orders/info")
    ReadOrderUserInfoResponse getUserInfo();
}
