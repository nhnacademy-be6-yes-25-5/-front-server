package com.nhnacademy.frontserver1.infrastructure.adaptor;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "userCouponAdaptor", url = "${eureka.gateway}/users")
public interface UserCouponAdaptor {

}
